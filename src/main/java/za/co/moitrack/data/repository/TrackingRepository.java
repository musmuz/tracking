package za.co.moitrack.data.repository;

import org.joda.time.DateTime;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import za.co.moitrack.data.model.Tracking;

import java.util.List;

/**
 * Created by nguni52 on 2/20/17.
 */
public interface TrackingRepository extends MongoRepository<Tracking, String>{
    Tracking findByImei(String imei);
    @Query(value = "{'vehicle.registrationNumber': ?0}")
    Tracking findByVehicleRegistration(String vehicleRegistration);

    @Query(value = "{'payload.gps.timestamp': {$gte: ?0, $lt: ?1} }")
    List<Tracking> findByTimeBetween5pmAndNow(DateTime dateFrom, DateTime dateTo, Sort sort);

    @Query(value = "{'payload.gps.timestamp': {$gte: ?0, $lt: ?1} }")
    List<Tracking> findByTimeBetween6amAndNow(DateTime dateFrom, DateTime dateTo, Sort sort);
}
