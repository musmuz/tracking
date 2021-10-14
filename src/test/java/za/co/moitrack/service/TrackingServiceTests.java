package za.co.moitrack.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import za.co.moitrack.DemoApplicationTests;
import za.co.moitrack.data.builder.TrackingBuilder;
import za.co.moitrack.data.builder.VehicleBuilder;
import za.co.moitrack.data.model.Tracking;
import za.co.moitrack.data.model.Vehicle;
import za.co.moitrack.data.repository.TrackingRepository;
import za.co.moitrack.data.repository.VehicleRepository;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by nguni52 on 2017/06/26.
 */
public class TrackingServiceTests extends DemoApplicationTests {
    private Log log = LogFactory.getLog(this.getClass().getName());

    @Autowired
    private TrackingRepository trackingRepository;
    @Autowired
    VehicleRepository vehicleRepository;
    @Autowired
    private TrackingService trackingService;

    private Tracking tracking;
    private Vehicle vehicle;

    @Before
    public void setup() {
        tracking = TrackingBuilder.BuildATracking(1);
        vehicle = VehicleBuilder.buildAVehicle(1);
        trackingRepository.save(tracking);
        vehicleRepository.save(vehicle);
    }


//    @Test
    public void getAllDataForDateRange() { }

    /**
     * This tests whether we can create a vehicle and save in the database.
     *
     */
    @Test
    public void createVehicleTest() {
        Tracking tracking1 = trackingService.create(tracking);
        assertEquals(tracking1, tracking);
        assertEquals(tracking.getImei(), vehicle.getImei());
    }

    /**
     * This tests retrieval of all the tracking records in the db.
     *
     */
    @Test
    public void listTracking() {
        log.info("testListTracking BEGAN");
        Collection<Tracking> trackings = trackingService.findAll();
        log.info("Size of trackings:: " + trackings.size() + "\n\n");
        log.info("testTracking ENDED");
    }

    /**
     * This tests whether we can get a list of trackings between 5pm and now.
     *
     */
    public void filterTrackingsAndSendEmailTest() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        log.info("The time is now - {} - " + dateFormat.format(new Date()));
        log.info("Cron Task :: Execution Time - {} - " + dateFormat.format(new Date()));
        DateTime now = new DateTime();
        DateTime previous = now.minusHours(12);
        Sort sort = new Sort(Sort.Direction.DESC, "payload.timeStamp");
        log.info("Previous:: " + previous.toString());
        log.info("Now:: " + now.toString());
        List<Tracking> trackingList = trackingRepository.findByTimeBetween5pmAndNow(previous, now, sort);

        trackingService.filterTrackingsAndSendEmail(trackingList, true);
    }

    /**
     * This tests the creating of a tracking record.
     *
     */
    @Test
    public void createTrackingTest() {
        log.info("Tracking in createTracking()");
        log.info("tracking in create()" + tracking.toString());
        Tracking newTracking = trackingService.create(tracking);
        assertEquals(newTracking.getImei(), tracking.getImei());
        log.info("createTrackingTest ENDED ");
    }

    /**
     * This tests retrieval of a record from the database.
     *
     */
    @Test
    public void findOneTest() {
        log.info("findOneTest BEGAN");
        Tracking newTracking = trackingService.findOne(tracking.getImei());
        log.info("New tracking in findOne" + newTracking.toString());
        assertEquals(newTracking.getImei(), tracking.getImei());
        log.info("findOneTest ENDED");
    }

    /**
     * This tests retrieval of a vehicle using a registration number.
     *
     */
    @Test
    public void findByVehicleRegistrationNumberTest() {
        log.info("findByVehicleregistrationNumberTest BEGAN");
        Tracking newTracking = trackingService.findByVehicleRegistration(vehicle.getRegistrationNumber());
        log.info("New tracking in findbyRegistrationNumber" + newTracking.toString());
        assertEquals(newTracking.getVehicle(), vehicle); // this will fail if there are any date time stamps in the object in the future
        assertEquals(newTracking.getImei(), vehicle.getImei());
        log.info(" findByVehicleregistrationNumberTest Ended");
    }
}
