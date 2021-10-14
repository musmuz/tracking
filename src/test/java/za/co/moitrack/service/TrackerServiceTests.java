package za.co.moitrack.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import za.co.moitrack.DemoApplicationTests;
import za.co.moitrack.data.builder.TrackerBuilder;
import za.co.moitrack.data.model.DriveState;
import za.co.moitrack.data.model.Location;
import za.co.moitrack.data.model.Tracker;
import za.co.moitrack.data.repository.TrackerRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import java.util.ArrayList;

/**
 * Created by nguni52 on 2017/04/05.
 */
public class TrackerServiceTests extends DemoApplicationTests {
    private Log log = LogFactory.getLog(this.getClass().getName());

    @Autowired
    private TrackerRepository trackerRepository;
    @Autowired
    private TrackerService trackerService;

    private List<Tracker> trackers;
    private Tracker tracker;

    private List<DriveState> driveStates;
    private DriveState driveState;
    @Before
    public void setUp() {
        trackers = TrackerBuilder.buildTrackers(10);
        trackerRepository.save(trackers);
    }

    @After
    public void tearDown() {
        trackerRepository.deleteAll();
    }

    @Test
    public void testListTracker() {
        log.info("testListTracker BEGAN");
        String imei = "12345551";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dateFrom = LocalDate.now().format(formatter).toString();
        String dateTo = LocalDate.now().format(formatter).toString();
        
        Collection<Tracker> trackingHistory = trackerService.findByImeiAndDate(imei, dateFrom, dateTo);

        log.info("testListTracker ENDED");
    }

    @Test
    public void testByImeiAndDateRange() {
        log.info("\n\n\ntestByImeiAndDateRange BEGAN");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        // find between yesterday and tomorrow
        String imei = "12345553";
        String dateFrom = LocalDate.now().minusDays(1).format(formatter);
        String dateTo = LocalDate.now().plusDays(1).format(formatter);
        List<Location> trackers = trackerService.findLocationsByImeiAndDate(imei, dateFrom, dateTo, true);

        log.info("Tracker size is: " + trackers.size());

        log.info("testByImeiAndDateRange ENDED\n\n\n");
    }

    @Test
    public void getByAllImeiAndDateRangeTest() {
        log.debug("\n\n\ngetByAllImeiAndDateRangeTest BEGAN");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        // find between yesterday and tomorrow
        String imei = "12345551";
        String dateFrom = LocalDate.now().minusDays(1).format(formatter);
        String dateTo = LocalDate.now().plusDays(1).format(formatter);
        List<Tracker> trackers = trackerService.findAllLocationsByImeiAndDate(imei, dateFrom, dateTo);

        log.debug("Tracker size is: " + trackers.size());

        log.debug("getByAllImeiAndDateRangeTest ENDED\n\n\n");
    }

    @Test
    public void testfindDriveStatesByImeiAndDateTest() {
        log.debug("\n\n\ngetDriveStatesByImeiAndDate");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String imei = "12345551";
        String dateFrom = LocalDate.now().minusDays(1).format(formatter);
        String dateTo = LocalDate.now().plusDays(1).format(formatter);
        List<DriveState> driveStates = trackerService.findDriveStatesByImeiAndDate(imei, dateFrom, dateTo);

        log.debug("DriveState size is: " + driveStates.size());

        log.debug("getDriveStatesByImeiAndDateTest ENDED\n\n\n");
    }

    @Test
    public void testGetDistinctTrackerPointsFail() {
        log.debug("\n\n\ngetByAllImeAndDageRangeFait Test BEGAN");
        List<Tracker> similarTrackers = new ArrayList<>();
        int count = 2;
        for(int i = 0; i < count; i++) {
            similarTrackers.add(TrackerBuilder.buildATracker(1));
            trackerRepository.save(similarTrackers);
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        // find between yesterday and tomorrow
        String imei = "12345551";
        String dateFrom = LocalDate.now().minusDays(1).format(formatter);
        String dateTo = LocalDate.now().plusDays(1).format(formatter);
        similarTrackers = trackerService.findAllLocationsByImeiAndDate(imei, dateFrom, dateTo);

        log.debug("Tracker size is: " + similarTrackers.size());
        log.debug("\n\n\ngetByAllImeAndDageRangeFait Test ENDED");
    }
}
