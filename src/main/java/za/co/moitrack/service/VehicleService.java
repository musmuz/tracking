package za.co.moitrack.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.co.moitrack.data.dto.GPSOdometerDTO;
import za.co.moitrack.data.model.Marker;
import za.co.moitrack.data.model.Vehicle;
import za.co.moitrack.data.repository.VehicleRepository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by nguni52 on 2/20/17.
 */
@Service
public class VehicleService {
    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private MarkerService markerService;
    @Autowired
    private OdometerService odometerService;
    private Log log = LogFactory.getLog(this.getClass().getName());


    public Collection<Vehicle> findAll() {
        return vehicleRepository.findAll();
    }

    /**
     * Create a new vehicle object and save it to the database only if it doesn't already exist in the database.
     *
     * @param vehicle - vehicle details
     * @return - either throws an exception if vehicle exists, or return vehicle being saved with new id, which is a PK
     */
    public Vehicle create(Vehicle vehicle) {
        throwExceptionIfVehicleExists(vehicle);
        return save(vehicle);
    }

    /**
     * Save a vehicle into the database
     *
     * @param vehicle - vehicle details
     * @return - vehicle details with the new primary key, id, from the database after saving.
     */
    public Vehicle save(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }

    /**
     * Find a vehicle using it's imei number
     *
     * @param imei - Imei number
     * @return - vehicle model
     */
    public Vehicle findOne(String imei) {
        return vehicleRepository.findByImei(imei);
    }

    /**
     * Check if a vehicle with imei and registration already exists. If it exists, then an exception with a message
     * is thrown.
     *
     * @param vehicle - The new vehicle we want to save
     */
    private void throwExceptionIfVehicleExists(Vehicle vehicle) {
        Vehicle existingVehicle = vehicleRepository.findByImeiAndRegistrationNumber(vehicle.getImei(), vehicle.getRegistrationNumber());
        if (existingVehicle != null) {
            throw new RuntimeException("Vehicle with registration number " + vehicle.getRegistrationNumber() +
                    " and IMEI " + vehicle.getImei() + " already exists.");
        }
    }

    public void delete(String vehicleId) {
        vehicleRepository.delete(vehicleId);
    }

    /**
     * @Todo - Mosa - Let's document what this method does.
     *
     *
     * @param vehicleRegistrations
     * @return
     */
    public List<GPSOdometerDTO> findVehicleGPSAndOdometer(String[] vehicleRegistrations) {
        List<GPSOdometerDTO> gpsOdometerDTOs = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        for (String vehicleRegistration : vehicleRegistrations) {
            try {
                Vehicle vehicle = vehicleRepository.findByRegistrationNumber(vehicleRegistration);
                if (vehicle != null) {
                    log.debug("My Vehicle: " + vehicle.toString() + "\n");
                    GPSOdometerDTO gpsOdometerDTO = new GPSOdometerDTO();
                    Marker marker = markerService.findByVehicleRegistration(vehicleRegistration);
                    if (marker != null) {
                        gpsOdometerDTO.setMarker(marker);
                        gpsOdometerDTO.setRegistrationNumber(vehicleRegistration);
                        String imei = vehicle.getImei();
                        String dateFrom = sdf.format(new Date(0));
                        String dateTo = sdf.format(new Date());

                        log.debug("IMEI: " + vehicle.getImei());
                        log.debug("Date From: " + dateFrom);
                        log.debug("Date To: " + dateTo + "\n\n");
                        gpsOdometerDTOs.add(gpsOdometerDTO);
                    }
                }
            } catch (Exception ex) {
                log.info("findVehicleGPSAndOdometer -- Error retrieving vehicle details for registration number: " + vehicleRegistration);
                log.debug(ex);
            }
        }

        return gpsOdometerDTOs;
    }
}
