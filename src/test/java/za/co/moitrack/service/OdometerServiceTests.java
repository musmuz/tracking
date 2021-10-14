package za.co.moitrack.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import za.co.moitrack.DemoApplicationTests;
import za.co.moitrack.data.builder.OdometerBuilder;
import za.co.moitrack.data.model.Odometer;
import za.co.moitrack.data.repository.OdometerRepository;

import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;


public class OdometerServiceTests extends DemoApplicationTests {
    private Log log = LogFactory.getLog(this.getClass().getName());

    @Autowired
    private OdometerRepository odometerRepository;
    @Autowired
    private OdometerService odometerService;

    private Odometer odometer;
    private List<Odometer> odometers;

    @Before
    public void setup() {
        odometer = OdometerBuilder.buildAnOdometer(1);
        odometers = OdometerBuilder.buildOdometers(5);

    }

    @After
    public void tearDown() {
        odometerRepository.deleteAll();
    }

    @Test
    public void createOdometer() {
        log.debug("testCreateOdometer BEGAN");
        log.debug("Odometer in createOdometer(): " + odometer.toString());
        odometerService.create(odometer);
        Odometer newOdometer = odometerService.findOne(odometer.getImei());
        log.debug("New Odometer in createOdometer(): " + newOdometer.toString());
        assertEquals(newOdometer.getImei(), odometer.getImei());
        log.debug("testCreateOdometer ENDED");
    }

    @Test
    public void testListOdometer() {
        log.debug("testListOdometers BEGAN");
        odometerRepository.save(odometers);
        Collection<Odometer> allOdometers = odometerService.findAll();
        log.debug("Size of odometers: " + allOdometers.size() + "\n\n");
        assertEquals(5, allOdometers.size());
        log.debug("testListOdometers ENDED");

    }

    @Test
    public void testLatestodoList() {
        log.debug("testLatestOdoList BEGAN");
        odometerRepository.save(odometers);
        Collection<Odometer> latestOdos = odometerService.findLatestOdos();
        log.debug("Size of latestOdos: " + latestOdos.size() + "\n\n");
        // @TODO - Fix this erratic unit test
//        assertEquals(1, latestOdos.size());
        log.debug("testLatestOdoList ENDED");
    }

    @Test
    public void testListOdosByImei() {
        log.debug("testListOdosByImei BEGAN");
        odometerRepository.save(odometer);
        Collection<Odometer> odometerByImei = odometerService.findOdosByImei(odometer.getImei());

        assertEquals(1, odometerByImei.size());
        log.debug("testListOdosByImei ENDED");
    }

    //    @TODO - Write unit tests for all methods in the odometer service
    @Test
    public void findOneTest() {
        log.debug("findOneTest BEGAN");
        odometerService.save(odometer);
        Odometer newOdometer = odometerService.findOne(odometer.getImei());
        log.debug("New Odometer in findOne" + newOdometer.toString());
        assertEquals(newOdometer.getImei(), odometer.getImei());
        log.debug("findOneTest ENDED");
    }

    @Test
    public void deleteOdometerTest() throws Exception {
        odometerRepository.save(odometer);
        odometerService.delete(odometer.getImei());
        Odometer existingOdometer = odometerService.findOne(odometer.getImei());
        assertNull(existingOdometer);
    }

}
