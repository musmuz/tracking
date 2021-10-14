package za.co.moitrack.data.repository;


import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.DeleteQuery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import za.co.moitrack.data.model.Odometer;

import java.util.Collection;
import java.util.List;

public interface OdometerRepository extends MongoRepository<Odometer, String> {

    @Query(value = "{'imei': {$eq: ?0}}")
    Odometer findByImei(String imei, Sort sort);
    @Query(value = "{'imei': {$eq: ?0}}")
    List<Odometer> findOdosByImei(String imei);

    @DeleteQuery
    void deleteByImei(String imei);
}
