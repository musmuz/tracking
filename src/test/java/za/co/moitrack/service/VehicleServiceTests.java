package za.co.moitrack.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import za.co.moitrack.DemoApplicationTests;
import za.co.moitrack.data.builder.VehicleBuilder;
import za.co.moitrack.data.model.Vehicle;
import za.co.moitrack.data.repository.VehicleRepository;

import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.BDDMockito.given;

/**
 * Created by nguni52 on 2017/03/24.
 */
public class VehicleServiceTests extends DemoApplicationTests {
    private Log log = LogFactory.getLog(this.getClass().getName());

    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private VehicleService vehicleService;

    private Vehicle vehicle;

    @Before
    public void setUp() {
        vehicle = VehicleBuilder.buildAVehicle(1);

    }

    @After
    public void tearDown() {
        vehicleRepository.deleteAll();
    }

    @Test(expected = RuntimeException.class)
    public void deleteVehicle() {
        log.info("testDeleteVehicle BEGAN");
        given(vehicleRepository.save(vehicle)).willReturn(vehicle);
        vehicleService.save(vehicle);
        Vehicle newVehicle = vehicleService.findOne(vehicle.getImei());
        vehicleService.delete(vehicle.getId());
        assertEquals(newVehicle.getImei(), vehicle.getImei());
        log.info("testDeleteVehicle ENDED");
    }

    /**
     * Test if we can add a vehicle to a database, and then delete it.
     *
     */
    @Test
    public void deleteVehicleTest() {
       Vehicle vehicle = VehicleBuilder.buildAVehicle(150);
       vehicleRepository.save(vehicle);
       vehicleService.delete(vehicle.getId());
       Vehicle existingVehicle = vehicleService.findOne(vehicle.getId());
       assertNull(existingVehicle);
    }

    /**
     * Test create vehicle and save into DB. Find the vehicle and assert that it was saved and we can retrieve it.
     *
     */
    @Test
    public void createVehicle() {
        log.info("testListVehicle BEGAN");
        vehicleService.create(vehicle);
        Vehicle newVehicle = vehicleService.findOne(vehicle.getImei());
        assertNotNull(newVehicle);
        assertEquals(newVehicle.getImei(), vehicle.getImei());
        assertEquals(newVehicle.getName(), vehicle.getName());
        log.info("testListVehicle ENDED");
    }

    @Test(expected = RuntimeException.class)
    public void throwExceptionCreateVehicleTest() {
        log.info("testListVehicle BEGAN");
        log.info("Vehicle in createVehicle(): " + vehicle.toString());
       //given(vehicleRepository.save(vehicle)).willReturn(vehicle);
        vehicleService.save(vehicle);
       Vehicle newVehicle = vehicleService.findOne(vehicle.getImei());
       log.info("NEW Vehicle in createVehicle(): " + newVehicle.toString());
       assertEquals(newVehicle.getImei(), vehicle.getImei());
       assertEquals(newVehicle.getName(), vehicle.getName());
       newVehicle = vehicleService.create(vehicle);

       assertEquals(newVehicle.getKey(), vehicle.getKey());
       assertEquals(newVehicle.getImei(), vehicle.getImei());
       log.info("testListVehicle ENDED");
    }

    @Test
    public void testListVehicle() {
        log.info("testListVehicle BEGAN");
        vehicleService.save(vehicle);
        Collection<Vehicle> vehicles = vehicleService.findAll();
        log.info("Size of vehicles:: " + vehicles.size() + "\n\n");
        log.info("testListVehicle ENDED");
    }
    /**
    *
    * Test to assert that when one vehicle is deleted, other vehicles can still be retrieved without a fail
     */

    @Test
    public void testListVehicleFail() {
        Collection<Vehicle> vehicles = VehicleBuilder.buildVehicles(10);
        String[] imeis = new String[vehicles.size()];

        int count = 0;
        for(Vehicle vehicle: vehicles) {
            imeis[count] = vehicle.getImei();
            count++;
        }
        vehicleService.delete(imeis[0]);
        Collection<Vehicle> newVehicles =  vehicleService.findAll();
        assertNotNull(newVehicles);

    }
}
