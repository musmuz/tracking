package za.co.moitrack.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import za.co.moitrack.data.model.Marker;
import za.co.moitrack.data.model.Odometer;
import za.co.moitrack.data.model.Tracking;
import za.co.moitrack.data.repository.OdometerRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by nguni52 on 2/21/17.
 */
@Service
public class MarkerService {
    @Autowired
    private TrackingService trackingService;
    @Autowired
    private OdometerRepository odometerRepository;
    private Log log = LogFactory.getLog(this.getClass().getName());


    public List<Marker> getMarkers() {
        Collection<Tracking> trackingCollection = trackingService.findAll();
        List<Marker> markers = new ArrayList<>();
        for (Tracking tracking : trackingCollection) {
            try {
                Sort sort = new Sort(Sort.Direction.DESC, "timeStamp");
                log.debug(tracking.toString());

                DateTime dt = tracking.getPayload().getGps().getTimestamp();
                DateTimeFormatter fmt = DateTimeFormat.forPattern("dd MMM yyyy hh:mm:ss a");
                String dtStr = fmt.print(dt);
                Odometer odometer = odometerRepository.findByImei(tracking.getImei(), sort);

                markers.add(new Marker(tracking.getImei(), Float.valueOf(tracking.getPayload().getGps().getLatitude()),
                        Float.valueOf(tracking.getPayload().getGps().getLongitude()), tracking.getVehicle().getDescription(),
                        false, dtStr, tracking.getCallInterface(),
                        tracking.getPayload().getGps().getSpeed(), odometer.getActualOdometer(), Float.valueOf(tracking.getPayload().getGps().getHeading()), tracking.getPayload().getTagging(), tracking.getPayload().getDriverBehaviour(), tracking.getPayload().getDriveStates()));

//                markers.add(new Marker(tracking.getImei(), String.valueOf(tracking.getPayload().getGps().getLatitude()),
//                        String.valueOf(tracking.getPayload().getGps().getLongitude()), tracking.getVehicle().getDescription(),
//                        false, tracking.getPayload().getTimeStamp(), tracking.getCallInterface(),
//                        tracking.getPayload().getGps().getSpeed(), tracking.getPayload().getDriveStates()));
            } catch (Exception ex) {
                log.debug("Error adding marker: ", ex);
            }
        }

        return markers;
    }

    public Marker findByVehicleRegistration(String vehicleRegistration) {
        Sort sort = new Sort(Sort.Direction.DESC, "timeStamp");
        Tracking tracking = trackingService.findByVehicleRegistration(vehicleRegistration);
        Odometer odometer = odometerRepository.findByImei(tracking.getImei(), sort);

        try {
            log.debug("Tracking info: " + tracking.toString());
            return new Marker(tracking.getImei(), Float.valueOf(tracking.getPayload().getGps().getLatitude()),
                    Float.valueOf(tracking.getPayload().getGps().getLongitude()), tracking.getVehicle().getDescription(),
                    false, tracking.getPayload().getGps().getTimestamp().toString(), tracking.getCallInterface(),
                    tracking.getPayload().getGps().getSpeed(), odometer.getActualOdometer(), Float.valueOf(tracking.getPayload().getGps().getHeading()), tracking.getPayload().getTagging(), tracking.getPayload().getDriverBehaviour(), tracking.getPayload().getDriveStates());
        } catch (Exception ex) {
            log.debug(ex);
            return null;
        }
    }

    public Marker findByImei(String imei) {
        Sort sort = new Sort(Sort.Direction.DESC, "timeStamp");
        Tracking tracking = trackingService.findOne(imei);
        Odometer odometer = odometerRepository.findByImei(imei, sort);


        log.debug("Tracking info: " + tracking.toString());
        return new Marker(tracking.getImei(), Float.valueOf(tracking.getPayload().getGps().getLatitude()),
                Float.valueOf(tracking.getPayload().getGps().getLongitude()), tracking.getVehicle().getDescription(),
                false, tracking.getPayload().getGps().getTimestamp().toString(), tracking.getCallInterface(),
                tracking.getPayload().getGps().getSpeed(), odometer.getActualOdometer(), Float.valueOf(tracking.getPayload().getGps().getHeading()), tracking.getPayload().getTagging(), tracking.getPayload().getDriverBehaviour(), tracking.getPayload().getDriveStates());
    }

    public List<Marker> findVehicleMarkers(String[] imeis) {
        List<Marker> vehicleMarkers = new ArrayList<>();
        for (String imei : imeis) {
            Sort sort = new Sort(Sort.Direction.DESC, "timeStamp");
            Tracking tracking = trackingService.findOne(imei);
            Odometer odometer = odometerRepository.findByImei(imei, sort);
            try {
                Marker marker = findByImei(imei);
                vehicleMarkers.add(marker);
            } catch (Exception ex) {
                log.info("Error retrieving vehicle marker details");
                log.debug(ex);
            }

        }
        return vehicleMarkers;
    }
}
