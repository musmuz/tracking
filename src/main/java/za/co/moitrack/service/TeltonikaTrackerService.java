package za.co.moitrack.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import za.co.moitrack.data.model.TeltonikaTracker;
import za.co.moitrack.data.repository.TeltonikaTrackerRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class TeltonikaTrackerService {
    @Autowired
    private TeltonikaTrackerRepository teltonikaTrackerRepository;
    private Log log = LogFactory.getLog(this.getClass().getName());

    private List<TeltonikaTracker> getDistinctTrackerPoints(Collection<TeltonikaTracker> teltonikaTrackerList) {

        double latitude;
        double longitude;
        double currentLatitude = 0.00;
        double currentLongitude = 0.00;

        List<TeltonikaTracker> trackerListResult = new ArrayList<>(teltonikaTrackerList);

        for (TeltonikaTracker tracker : teltonikaTrackerList) {

            latitude = tracker.getGps().getLatitude();
            longitude = tracker.getGps().getLongitude();

            log.debug("Latitude: " + latitude);
            log.debug("Longitude: " + longitude);
            log.debug("currentLatitude: " + currentLatitude);
            log.debug("currentLongitude: " + currentLongitude);

            if (currentLatitude == latitude && currentLongitude == longitude) {
                trackerListResult.remove(tracker);
            }

            currentLatitude = latitude;
            currentLongitude = longitude;
        }

        return trackerListResult;
    }

    public List<List<TeltonikaTracker>> findTripListByImeiAndDate(long imei, String dateFrom, String dateTo) {
        try {
            Sort sort = new Sort(Sort.Direction.DESC, "timestamp");
            DateTime dateTimeFrom = DateTime.parse(dateFrom, DateTimeFormat.forPattern("dd/MM/yyyy"));
            DateTime dateTimeTo = DateTime.parse(dateTo, DateTimeFormat.forPattern("dd/MM/yyyy")).plusDays(1);

            Collection<TeltonikaTracker> trackerList = teltonikaTrackerRepository.findTrackingUnitDataByImeiAndDateBetween(imei, dateTimeFrom, dateTimeTo, sort);
            List<TeltonikaTracker> distinctTrackerList = getDistinctTrackerPoints(trackerList);

            List<List<TeltonikaTracker>> finalTrackerList = SegmentIntoTrips(distinctTrackerList);

            return finalTrackerList;

        } catch (Exception ex) {
            log.debug(ex);
            throw new RuntimeException("Exception: " + ex);
        }
    }


    private List<List<TeltonikaTracker>> SegmentIntoTrips(List<TeltonikaTracker> teltonikaDistinctTrackerList) {
        List<List<TeltonikaTracker>> outputTrackerList = new ArrayList<>();
        List<TeltonikaTracker> iterator = new ArrayList<>();

        DateTime initialDateTime = teltonikaDistinctTrackerList.get(0).getTimestamp();

        for (TeltonikaTracker tracker : teltonikaDistinctTrackerList) {
            DateTime dateTime = tracker.getTimestamp();
            if (initialDateTime.compareTo(dateTime.plusMinutes(7)) > 0 || iterator.size() > 99) {
                outputTrackerList.add(new ArrayList<>(iterator));
                iterator.clear();

                iterator.add(tracker);
                initialDateTime = dateTime;

                continue;
            }

            iterator.add(tracker);
            initialDateTime = dateTime;
        }

        if (!iterator.isEmpty() && iterator.size() > 1) {
            outputTrackerList.add(iterator);
        }
        return outputTrackerList;
    }
}
