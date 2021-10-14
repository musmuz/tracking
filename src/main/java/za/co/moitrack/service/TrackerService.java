package za.co.moitrack.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import za.co.moitrack.data.model.DriveState;
import za.co.moitrack.data.model.Location;
import za.co.moitrack.data.model.Tracker;
import za.co.moitrack.data.repository.TrackerRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by nguni52 on 2017/04/05.
 */
@Service
public class TrackerService {
    @Autowired
    private TrackerRepository trackerRepository;
    private Log log = LogFactory.getLog(this.getClass().getName());

    public Collection<Tracker> findByImeiAndDate(String imei, String dateFrom, String dateTo) {

        DateTime DateTimeFrom = DateTime.parse(dateFrom, DateTimeFormat.forPattern("dd/MM/yyyy"));
        return trackerRepository.findByImeiAndDate(imei, DateTimeFrom, new Sort(Sort.Direction.DESC, "id"));
    }

    public List<DriveState> findDriveStatesByImeiAndDate(String imei, String dateFrom, String dateTo) {
        int pageNumber = 1;
        final int PAGE_SIZE = 100;

        List<DriveState> driveStates = new ArrayList<>();

        Sort sort = new Sort(Sort.Direction.DESC, "data.payload.timeStamp");
        Pageable pageable = new PageRequest(pageNumber - 1, PAGE_SIZE, sort);

        DateTime dateTimeFrom = DateTime.parse(dateFrom, DateTimeFormat.forPattern("dd/MM/yyyy"));
        DateTime dateTimeTo = DateTime.parse(dateTo, DateTimeFormat.forPattern("dd/MM/yyyy"));

        try {
            Collection<Tracker> trackerList = trackerRepository.findDriveStatesByImeiAndDateBetween(imei, dateTimeFrom, dateTimeTo, sort);
            for (Tracker tracker : trackerList) {
                DriveState driveState = tracker.getData().getPayload().getDriveStates();
                driveStates.add(driveState);
            }

        } catch (Exception ex) {
            log.debug(ex);
            throw new RuntimeException("Exception: " + ex);
        }
        return driveStates;

    }

    public List<Location> findLocationsByImeiAndDate(String imei, String dateFrom, String dateTo, Boolean limitOuput) {

        int pageNumber = 1;
        final int PAGE_SIZE = 100;

        Sort sort = new Sort(Sort.Direction.DESC, "data.payload.timeStamp");
        Pageable pageable = new PageRequest(pageNumber - 1, PAGE_SIZE, sort);

        DateTime dateTimeFrom = DateTime.parse(dateFrom, DateTimeFormat.forPattern("dd/MM/yyyy"));
        DateTime dateTimeTo = DateTime.parse(dateTo, DateTimeFormat.forPattern("dd/MM/yyyy"));

        log.debug("Date Time From: " + DateTimeFormat.forPattern("dd/MM/yyyy").print(dateTimeFrom));
        log.debug("Date Time To: " + DateTimeFormat.forPattern("dd/MM/yyyy").print(dateTimeTo));

        int numberOfDays = 0;
        try {

            Collection<Tracker> trackerList = trackerRepository.findLocationsByImeiAndDateBetween(imei, dateTimeFrom, dateTimeTo, pageable).getContent();

            List<Location> locations = stripLocationsFromTrackerList(getDistinctTrackerPoints(trackerList));

            log.debug("TRACKER SIZE: " + trackerList.size());

            if (limitOuput && locations.size() > PAGE_SIZE) {
                return locations.subList(0, PAGE_SIZE);
            }

            return locations;

        } catch (Exception ex) {
            log.debug(ex);
            throw new RuntimeException("Exception: " + ex);
        }
    }

    private List<Location> stripLocationsFromTrackerList(List<Tracker> trackerList) {

        double latitude;
        double longitude;
        List<Location> locations = new ArrayList<>();

        for (Tracker tracker : trackerList) {

            latitude = Double.parseDouble(tracker.getData().getPayload().getGps().getLatitude());
            longitude = Double.parseDouble(tracker.getData().getPayload().getGps().getLongitude());

            Location location = new Location(latitude, longitude);
            locations.add(location);

        }

        return locations;
    }


    private List<Tracker> getDistinctTrackerPoints(Collection<Tracker> trackerList) {

        double latitude;
        double longitude;
        double currentLatitude = 0.00f;
        double currentLongitude = 0.00f;

        List<Tracker> trackerListResult = new ArrayList<>(trackerList);

        for (Tracker tracker : trackerList) {

            latitude = Double.parseDouble(tracker.getData().getPayload().getGps().getLatitude());
            longitude = Double.parseDouble(tracker.getData().getPayload().getGps().getLongitude());

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

    public List<Tracker> findAllLocationsByImeiAndDate(String imei, String dateFrom, String dateTo) {
        int pageNumber = 1;
        final int PAGE_SIZE = 90;

        try {
            Sort sort = new Sort(Sort.Direction.DESC, "data.payload.timeStamp");
            Pageable pageable = new PageRequest(pageNumber - 1, PAGE_SIZE, sort);
            DateTime dateTimeFrom = DateTime.parse(dateFrom, DateTimeFormat.forPattern("dd/MM/yyyy"));
            DateTime dateTimeTo = DateTime.parse(dateTo, DateTimeFormat.forPattern("dd/MM/yyyy")).plusDays(1);

            Collection<Tracker> trackerList = trackerRepository.findTrackingUnitDataByImeiAndDateBetween(imei, dateTimeFrom, dateTimeTo, sort);
            List<Tracker> finaltrackerList = getDistinctTrackerPoints(trackerList);

        	/* if (finaltrackerList.size() > PAGE_SIZE) {
        		 finaltrackerList.subList(0, PAGE_SIZE-1);
             }w
        	*/
            return finaltrackerList;

        } catch (Exception ex) {
            log.debug(ex);
            throw new RuntimeException("Exception: " + ex);
        }
    }


    public List<List<Tracker>> FindTripListByImeiAndDate(String imei, String dateFrom, String dateTo) {
        try {
            Sort sort = new Sort(Sort.Direction.DESC, "data.payload.timeStamp");
            DateTime dateTimeFrom = DateTime.parse(dateFrom, DateTimeFormat.forPattern("dd/MM/yyyy"));
            DateTime dateTimeTo = DateTime.parse(dateTo, DateTimeFormat.forPattern("dd/MM/yyyy")).plusDays(1);

            Collection<Tracker> trackerList = trackerRepository.findTrackingUnitDataByImeiAndDateBetween(imei, dateTimeFrom, dateTimeTo, sort);
            List<Tracker> distinctTrackerList = getDistinctTrackerPoints(trackerList);

            List<List<Tracker>> finalTrackerList = SegmentIntoTrips(distinctTrackerList);

            return finalTrackerList;

        } catch (Exception ex) {
            log.debug(ex);
            throw new RuntimeException("Exception: " + ex);
        }
    }

    private List<List<Tracker>> SegmentIntoTrips(List<Tracker> distinctTrackerList) {
        List<List<Tracker>> outputTrackerList = new ArrayList<>();
        List<Tracker> iterator = new ArrayList<>();

        DateTime initialDateTime = distinctTrackerList.get(0).getData().getPayload().getTimeStamp();

        for (Tracker tracker : distinctTrackerList) {
            DateTime dateTime = tracker.getData().getPayload().getTimeStamp();
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
