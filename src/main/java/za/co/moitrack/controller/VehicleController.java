package za.co.moitrack.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.*;
import za.co.moitrack.data.dto.GPSOdometerDTO;
import za.co.moitrack.data.model.ServerResponse;
import za.co.moitrack.data.model.Vehicle;
import za.co.moitrack.service.VehicleService;

import java.util.Collection;
import java.util.List;

/**
 * Created by nguni52 on 2/20/17.
 */
@RestController
@RequestMapping(value = VehicleController.VEHICLE)
public class VehicleController extends MainController {

    public static final String VEHICLE = "vehicle";
    public static final String VEHICLES = "vehicles";
    private static final String IMEI = "imei";
    private static final String HOME_IMEI = HOME + "{" + IMEI + "}";
    public static final String HOME_ID = HOME + "{" + ID + "}";
    public static final String REGISTRATION = "registration";
    private static final String FINDBY = "findby";
    private static final String HOME_FINDBY_REGISTRATION = HOME + FINDBY + HOME + REGISTRATION;

    @Autowired
    private VehicleService vehicleService;
    @Autowired
    private MessageSource messageSource;
    private Log log = LogFactory.getLog(this.getClass().getName());

    @GetMapping(HOME_LIST)
    public Collection<Vehicle> findAllVehicles() {
        return vehicleService.findAll();
    }

    @PostMapping(HOME_CREATE)
    public Vehicle createVehicle(@RequestBody Vehicle vehicle) {
        return vehicleService.create(vehicle);
    }

    @GetMapping(HOME_IMEI)
    public Vehicle findOne(@PathVariable(IMEI) String imei) {
        return vehicleService.findOne(imei);
    }

    @PutMapping
    public ServerResponse saveVehicle(@RequestBody Vehicle vehicle) {
        String message;
        boolean status = true;
        ServerResponse serverResponse = new ServerResponse();
        try {
            vehicleService.save(vehicle);
            message = messageSource.getMessage("vehicle.save.success", null, null);
        } catch(Exception ex) {
            message = messageSource.getMessage("vehicle.save.failure", null, null);
            status = false;
        }
        serverResponse.setStatus(status);
        serverResponse.setMessage(message);

        return serverResponse;
    }

    @DeleteMapping(HOME_ID)
    public ServerResponse deleteVehicle(@PathVariable(ID) String vehicleId) {
        String message;
        boolean status = true;
        ServerResponse serverResponse = new ServerResponse();
        try {
            vehicleService.delete(vehicleId);
            message = messageSource.getMessage("vehicle.delete.success", null, null);
        } catch(Exception ex) {
            message = messageSource.getMessage("vehicle.delete.failure", null, null);
            status = false;
        }
        serverResponse.setStatus(status);
        serverResponse.setMessage(message);

        return serverResponse;
    }

    @GetMapping(HOME_FINDBY_REGISTRATION)
    public List<GPSOdometerDTO> getGPSAndOdometer(@RequestParam(VEHICLES) String[] vehicles) {
        return vehicleService.findVehicleGPSAndOdometer(vehicles);
    }
}
