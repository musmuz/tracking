package za.co.moitrack.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import za.co.moitrack.data.model.Tracking;
import za.co.moitrack.data.model.Vehicle;
import za.co.moitrack.data.repository.TrackingRepository;
import za.co.moitrack.data.repository.VehicleReportRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Created by nguni52 on 2/20/17.
 */
@Service
public class TrackingService {
    @Autowired
    TrackingRepository trackingRepository;
    @Autowired
    private VehicleService vehicleService;
    @Autowired
    private MailService mailService;
    @Autowired
    private VehicleReportService vehicleReportService;
    @Autowired
    private VehicleReportRepository vehicleReportRepository;
    private Log log = LogFactory.getLog(this.getClass().getName());
    @Value("${moifleet.api.link}")
    private String moifleetAPILink;

    public Tracking create(Tracking tracking) {
        Vehicle vehicle = vehicleService.findOne(tracking.getImei());
        tracking.setVehicle(vehicle);
        return trackingRepository.save(tracking);
    }

    public Collection<Tracking> findAll() {
        return trackingRepository.findAll();
    }

    public Tracking findByVehicleRegistration(String vehicleRegistration) {
        return trackingRepository.findByVehicleRegistration(vehicleRegistration);
    }


    public void filterTrackingsAndSendEmail(List<Tracking> trackingList, boolean daily) {
        RestTemplate restTemplate = new RestTemplate();

        Vehicle[] response = restTemplate.getForObject(moifleetAPILink, Vehicle[].class);
        List<Vehicle> vehicleList = Arrays.asList(response);
        Collection<Vehicle> vehicles = new ArrayList<Vehicle>(vehicleList);
        log.debug("List of trackers: " + trackingList.size());
        log.debug("VEHICLE SIZE" + vehicles.size());

        // get all tracking vehicles
        List<Vehicle> trackingVehicles = new ArrayList<>();
        for (Tracking tracking : trackingList) {
            try {
                if (tracking.getVehicle().getRegistrationNumber() != null) {
                    trackingVehicles.add(tracking.getVehicle());
                } else {
                    log.info("Tracking vehicle is null for + " + tracking.toString());
                }
            } catch (NullPointerException npe) {
                log.info("Tracking vehicle is null for + " + tracking.toString());
            }
        }
        log.debug("Tracking size after removing nulls: " + trackingVehicles.size());

        // go and remove all the vehicles that are not in tracking
        int[] indexes = new int[vehicles.size()];
        Arrays.fill(indexes, -1);
        int count = 0; // keeps track of the vehicles that we are iterating through.
        int i = 0;
        for (Vehicle vehicle : vehicles) {
            for (Tracking tracking : trackingList) {
                try {
                    if (!tracking.getVehicle().getRegistrationNumber().isEmpty()) {
                        if (vehicle.getRegistrationNumber().equalsIgnoreCase(tracking.getVehicle().getRegistrationNumber())) {
                            indexes[i] = count;
                            i++;
                            break;
                        }
                    }
                } catch (NullPointerException npe) {
                    log.error(npe);
                }
            }
            count++;
        }

        Vehicle vehicle;
        List<Vehicle> newVehicleList = new ArrayList<>(vehicles);

        for (int j : indexes) {
            if (j != -1) {
                log.debug("Vehicle: " + newVehicleList.get(j).toString());
                log.debug("Current index: " + j);
                vehicle = newVehicleList.get(j);
                vehicles.remove(vehicle);
            }
        }

        log.debug("The size of the vehicles::: " + vehicles.size());

        // @TODO take a look at this
        int totalVehicles = vehicles.size() + trackingVehicles.size();

        mailService.sendNumberOfReportingUnits(totalVehicles, vehicles, trackingVehicles, daily);

        /** Store the daily report in the database **/
        vehicleReportService.saveVehicleReport(totalVehicles, vehicles, trackingVehicles);
    }


    public Tracking findOne(String imei) {
        return trackingRepository.findByImei(imei);
    }
}
