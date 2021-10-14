package za.co.moitrack.service;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import za.co.moitrack.DemoApplicationTests;
import za.co.moitrack.data.builder.OdometerBuilder;
import za.co.moitrack.data.builder.TrackingBuilder;
import za.co.moitrack.data.builder.VehicleBuilder;
import za.co.moitrack.data.model.Marker;
import za.co.moitrack.data.model.Odometer;
import za.co.moitrack.data.model.Tracking;
import za.co.moitrack.data.model.Vehicle;
import za.co.moitrack.data.repository.OdometerRepository;
import za.co.moitrack.data.repository.TrackerRepository;
import za.co.moitrack.data.repository.TrackingRepository;
import za.co.moitrack.data.repository.VehicleRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class MarkerServiceTests extends DemoApplicationTests {
    @Autowired
    TrackingRepository trackingRepository;
    @Autowired
    VehicleRepository vehicleRepository;
    @Autowired
    OdometerRepository odometerRepository;
    private Log log = LogFactory.getLog(this.getClass().getName());
    @Autowired
    private MarkerService markerService;
    @Autowired
    private TrackingService trackingService;
    private Tracking tracking;
    private Vehicle vehicle;
    private Odometer odometer;
    private List<Marker> marker;
    private List<Marker> markers = new ArrayList<>();
    @Autowired
    private TrackerRepository trackerRepository;

    @Before
    public void setup() {
        // markers = MarkerBuilder.buildMarkers(1);
        vehicle = VehicleBuilder.buildAVehicle(1);
        tracking = TrackingBuilder.BuildATracking(1);
        odometer = OdometerBuilder.buildAnOdometer(1);
        trackingRepository.save(tracking);
        vehicleRepository.save(vehicle);
        odometerRepository.save(odometer);
    }

    @Test
    public void listMarkers() {
        log.debug("testListMarkers BEGAN");
        // given( trackingRepository.save(tracking)).willReturn(tracking);
        // given( vehicleRepository.save(vehicle)).willReturn(vehicle);

        DateTime dt = tracking.getPayload().getGps().getTimestamp();
        DateTimeFormatter fmt = DateTimeFormat.forPattern("dd MMM yyyy hh:mm:ss a");
        String dtStr = fmt.print(dt);
        Sort sort = new Sort(Sort.Direction.DESC, "timeStamp");
        Odometer odometerReading = odometerRepository.findByImei(tracking.getImei(), sort);
        markers.add((new Marker(tracking.getImei(), Float.valueOf(tracking.getPayload().getGps().getLatitude()),
                Float.valueOf(tracking.getPayload().getGps().getLongitude()), tracking.getVehicle().getDescription(),
                false, dtStr, tracking.getCallInterface(),
                tracking.getPayload().getGps().getSpeed(), odometerReading.getActualOdometer(), Float.valueOf(tracking.getPayload().getGps().getHeading()), tracking.getPayload().getTagging(), tracking.getPayload().getDriverBehaviour(), tracking.getPayload().getDriveStates())));
        marker = markerService.getMarkers();
        assertEquals(markers, marker);
        log.debug("Size of markers:: " + marker.size() + "\n\n");
        log.debug("testListMarkers ENDED");
    }

    @Test
    public void testFindByVehicleRegistration() {
        Vehicle vehicle = VehicleBuilder.buildAVehicle(1);
        Marker marker = markerService.findByVehicleRegistration(vehicle.getRegistrationNumber());
        assertNotNull(marker);
    }

    @Test(expected = NullPointerException.class)
    public void testFindByVehicleRegistrationFail() {
        Vehicle vehicle = VehicleBuilder.buildAVehicle(1);
        Marker marker = markerService.findByVehicleRegistration(vehicle.getImei());
        assertNotNull(marker);
    }

    @Test
    public void findUsingImei() {
        log.debug("test Find a marker by imei BEGAN");
        Vehicle vehicle = VehicleBuilder.buildAVehicle(1);
        Marker marker = markerService.findByImei(vehicle.getImei());
        assertNotNull(marker);
    }

    @Test(expected = NullPointerException.class)
    public void findUsingImeiFail() {
        log.debug("test Find a marker by imei BEGAN");
        Tracking tracking = TrackingBuilder.BuildATracking(1);
        tracking.setPayload(null);
        trackingRepository.save(tracking);
        Marker marker = markerService.findByImei(tracking.getImei());
//        assertNotNull(marker);
    }

    @Test
    public void testFindVehicleMarkers() {
        List<Vehicle> vehicles = VehicleBuilder.buildVehicles(10);
        String[] imeis = new String[vehicles.size()];

        int count = 0;
        for (Vehicle vehicle : vehicles) {
            imeis[count] = vehicle.getImei();
            count++;
        }
        List<Marker> markers = markerService.findVehicleMarkers(imeis);
        assertNotNull(markers);

    }


    @Test
    public void testFindVehicleMarkersFail() {
        List<Vehicle> vehicles = VehicleBuilder.buildVehicles(10);
        String[] imeis = new String[vehicles.size()];

        int count = 0;
        for (Vehicle vehicle : vehicles) {
            imeis[count] = vehicle.getImei();
            count++;
        }
        imeis[0] = null;
        List<Marker> markers = markerService.findVehicleMarkers(imeis);
        assertNotNull(markers);

    }
}
