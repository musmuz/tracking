package za.co.moitrack.service;

import com.grum.geocalc.DegreeCoordinate;
import com.grum.geocalc.EarthCalc;
import com.grum.geocalc.Point;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import za.co.moitrack.data.model.Odometer;
import za.co.moitrack.data.model.Tracker;
import za.co.moitrack.data.model.Tracking;
import za.co.moitrack.data.repository.OdometerRepository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;


/**
 * Created by nguni52 on 2/21/17.
 */
@Service
public class OdometerService {
    public static final double EARTH_DIAMETER = 6371.01 * 1000; //meters
    private Log log = LogFactory.getLog(this.getClass().getName());
    @Autowired
    private TrackerService trackerService;
    @Autowired
    private TrackingService trackingService;
    @Autowired
    private OdometerRepository odometerRepository;
    // @Autowired
    //private OdometerTasks odometerTasks;

    public Collection<Odometer> findAll() {
        return odometerRepository.findAll();
    }

    public Odometer create(Odometer odometer) {
        return save(odometer);
    }

    public Odometer save(Odometer odometer) {
        return odometerRepository.save(odometer);
    }

    public Odometer findOne(String imei) {
        Sort sort = new Sort(Sort.Direction.DESC, "timeStamp");
        return odometerRepository.findByImei(imei, sort);
    }

    public Collection<Odometer> findLatestOdos() {
        List<Odometer> latestOdos = new ArrayList<>();
        Collection<Tracking> trackings = trackingService.findAll();
        for (Tracking tracking : trackings) {
            String imei = tracking.getImei();
//            String regex = "\\d{15}+$";
//            Pattern pattern = Pattern.compile(regex);
//            Matcher matcher = pattern.matcher(imei);
//            if (matcher.matches()) {
//                Odometer latestOdo = findOne(imei);
//                LatestOdos.add(latestOdo);
//            }
            Odometer latestOdo = findOne(imei);
            if (latestOdo != null) {
                latestOdos.add(latestOdo);
            }
        }
        return latestOdos;
    }

    public Collection<Odometer> findOdosByImei(String imei) {

        return odometerRepository.findOdosByImei(imei);
    }

    public void delete(String odometerImei) {
        odometerRepository.deleteByImei(odometerImei);
    }


    public boolean isValid(Odometer odometer) {
        return odometer != null
                && odometer.getId() != null
                && odometer.getImei() != null
                && odometer.getTimeStamp() != null
                && odometer.getActualOdometer() != null
                && odometer.getZeroOdometer() != null
                && odometer.getAlertFlag() != null;
    }

    public float getDistance(String imei, String dateFrom, String dateTo) {
        log.debug("Distance: " + dateFrom + " Date To: " + dateTo);

        List<Tracker> trackerList = trackerService.findAllLocationsByImeiAndDate(imei, dateFrom, dateTo);
        log.debug("Size of tracker list is: " + trackerList.size());

        float result = 0;

        if (trackerList == null || trackerList.isEmpty()) {
            return result;
        }
        double latitude;
        double longitude;
        double currentLatitude = Double.parseDouble(trackerList.get(0).getData().getPayload().getGps().getLatitude());
        double currentLongitude = Double.parseDouble(trackerList.get(0).getData().getPayload().getGps().getLongitude());

        for (Tracker tracker : trackerList) {
            latitude = Double.parseDouble(tracker.getData().getPayload().getGps().getLatitude());
            longitude = Double.parseDouble(tracker.getData().getPayload().getGps().getLongitude());

            double distance = distanceBetween(latitude, currentLatitude, longitude, currentLongitude);

            Point startingPoint = new Point(new DegreeCoordinate(currentLatitude), new DegreeCoordinate(currentLongitude));
            Point destinationPoint = new Point(new DegreeCoordinate(latitude), new DegreeCoordinate(longitude));
            double distanceLib = EarthCalc.getDistance(startingPoint, destinationPoint);
            log.debug("Result distance between so far: " + distance);
            log.debug("Result distance between so far via earth calc: " + distanceLib);

            result += distance;

        }

        return result;
    }

    private double distanceBetween(double lat1, double lat2, double lon1, double lon2) {

        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R * c * 1000; // convert to meters
    }

    public float getDistanceWithoutDates(String imei) {
        Date startDate = new Date(0);
        Date currentDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String formattedStartDate = sdf.format(startDate);

        String formattedEndDate = sdf.format(currentDate);
        log.debug("Formatted start date is: " + formattedStartDate);
        log.debug("Formatted end date is: " + formattedEndDate);
        return this.getDistance(imei, formattedStartDate, formattedEndDate);
    }
}
