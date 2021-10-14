package za.co.moitrack.data.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import za.co.moitrack.data.model.Vehicle;

/**
 * Created by nguni52 on 2/20/17.
 */
public interface  VehicleRepository extends MongoRepository<Vehicle, String>{
    Vehicle findByImeiAndRegistrationNumber(String imei, String registrationNumber);
    Vehicle findByImei(String imei);
    Vehicle findByRegistrationNumber(String registrationNumber);
}
