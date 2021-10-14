package za.co.moitrack.scheduler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import za.co.moitrack.DemoApplicationTests;
import za.co.moitrack.data.builder.OdometerBuilder;
import za.co.moitrack.data.builder.TrackingBuilder;
import za.co.moitrack.data.model.Odometer;
import za.co.moitrack.data.model.Tracking;
import za.co.moitrack.data.repository.OdometerRepository;
import za.co.moitrack.data.repository.TrackingRepository;
import za.co.moitrack.service.OdometerService;
import za.co.moitrack.service.VehicleService;

import java.util.List;

public class OdometerTasksTests extends DemoApplicationTests {
    @Autowired
    private OdometerTasks odometerTasks;
    private Log log = LogFactory.getLog(this.getClass().getName());
    @Autowired
    private OdometerRepository odometerRepository;
    @Autowired
    private TrackingRepository trackingRepository;
    private List<Odometer> odometerList;

    @Before
    public void setUp() {
        odometerList = OdometerBuilder.buildOdometers(10);
    }

    @After
    public void tearDown() {
        odometerRepository.deleteAll();
        trackingRepository.deleteAll();
    }

    @Test
    public void calculateOdometersTest() {
        log.debug("************* BEGIN Calculating Odometers ***********");
        odometerRepository.save(odometerList);
        for(Odometer odometer: odometerList) {
            Tracking tracking = TrackingBuilder.buildATracking(1, odometer.getImei());
            trackingRepository.save(tracking);
        }
        odometerTasks.calculateOdometers();
        log.debug("************* END Calculating Odometers ***********");
    }
}
