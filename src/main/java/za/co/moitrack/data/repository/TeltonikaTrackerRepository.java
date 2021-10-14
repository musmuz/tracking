package za.co.moitrack.data.repository;

import org.joda.time.DateTime;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import za.co.moitrack.data.model.TeltonikaTracker;

import java.util.Collection;

public interface TeltonikaTrackerRepository extends MongoRepository<TeltonikaTracker, String> {
    @Query(value = "{'imei': {$eq: ?0}}")
    TeltonikaTracker findByImei(long imei, Sort sort);

    @Query(value = "{'imei': {$eq: ?0}}")
    Collection<TeltonikaTracker> findTeltonikaTrackersByImei(long imei);

    @Query(value = "{'imei': {$eq: ?0}, 'timestamp': {$gte: ?1, $lt: ?2} }")
    public Collection<TeltonikaTracker> findTrackingUnitDataByImeiAndDateBetween(long imei, DateTime dateFrom, DateTime dateTo, Sort sort);
}