package za.co.moitrack.scheduler;

import net.javacrumbs.shedlock.core.SchedulerLock;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import za.co.moitrack.data.model.Tracking;
import za.co.moitrack.data.repository.TrackingRepository;
import za.co.moitrack.service.MailService;
import za.co.moitrack.service.TrackingService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
public class ScheduledTasks {
    private static final Log log = LogFactory.getLog(ScheduledTasks.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    @Autowired
    private TrackingRepository trackingRepository;
    @Autowired
    private MailService mailService;
    @Autowired
    private TrackingService trackingService;
    @Value("${moitrack.reports.daily}")
    private boolean isDailyReport;

    /**
     * To test the cron job and whether it will send information correctly, the following method is used.
     */
    // @Scheduled(cron = "0 0/5 * * * *")
    public void reportCurrentTime() {
        log.debug("The time is now - {} - " + dateFormat.format(new Date()));
        log.debug("Cron Task :: Execution Time - {} - " + dateFormat.format(new Date()));
        DateTime now = new DateTime();
        DateTime previous = now.minusHours(24);
        Sort sort = new Sort(Sort.Direction.DESC, "payload.timeStamp");
        log.debug("Previous:: " + previous.toString());
        log.debug("Now:: " + now.toString());
        List<Tracking> trackingList = trackingRepository.findByTimeBetween6amAndNow(previous, now, sort);

        trackingService.filterTrackingsAndSendEmail(trackingList, isDailyReport);
    }

    /**
     * This cron job runs at 6am every morning. It uses the greenwich meridian time, and South Africa is 2 hours ahead
     * and therefore the time is showing at 4am. The cron checks all the vehicles that updated the tracking devices
     * in the past 24 hours, and sends an email with this report.
     *     @Scheduled(cron = "0 \* /1 \* \* \*   ")
     */
    @Scheduled(cron = "0 0 3 * * *")
    @SchedulerLock(name = "EmailTask", lockAtMostForString = "PT4M", lockAtLeastForString = "PT4M")
    public void scheduleMorningVehicleTripRecords() {
        sendEmail();
    }

    //    @Scheduled(cron = "0 0 16 * * *")
    public void scheduleAfternoonVehicleTripRecords() {
        sendEmail();
    }

    private void sendEmail() {
        log.debug("Cron Task :: Execution Time - {} - " + dateFormat.format(new Date()));
        DateTime now = new DateTime();
        DateTime previous = now.minusHours(48);
        Sort sort = new Sort(Sort.Direction.DESC, "payload.timeStamp");
        log.debug("Previous:: " + previous.toString());
        log.debug("Now:: " + now.toString());
        List<Tracking> trackingList = trackingRepository.findByTimeBetween6amAndNow(previous, now, sort);
        log.debug("List of trackers: " + trackingList.size());
        trackingService.filterTrackingsAndSendEmail(trackingList, isDailyReport);
    }

    @Scheduled(cron = "0 30 8 * * 1")
    @SchedulerLock(name = "EmailTask", lockAtMostForString = "PT4M", lockAtLeastForString = "PT4M")
    public void scheduleWeeklyReport () {
        sendWeeklyEmail();
    }

    private void sendWeeklyEmail() {
        log.debug("Cron Task :: Execution Time - {} - " + dateFormat.format(new Date()));
        DateTime now = new DateTime();
        DateTime previous = now.minusDays(7);
        Sort sort = new Sort(Sort.Direction.DESC, "payload.timeStamp");
        log.debug("Previous:: " + previous.toString());
        log.debug("Now:: " + now.toString());
        List<Tracking> trackingList = trackingRepository.findByTimeBetween6amAndNow(previous, now, sort);
        log.debug("List of trackers: " + trackingList.size());
        trackingService.filterTrackingsAndSendEmail(trackingList, !isDailyReport);
    }

}
