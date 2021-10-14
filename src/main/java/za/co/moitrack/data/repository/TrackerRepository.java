package za.co.moitrack.data.repository;

import org.joda.time.DateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import za.co.moitrack.data.model.Tracker;

import java.util.Collection;

/**
 * Created by nguni52 on 2017/04/05.
 */
public interface TrackerRepository extends MongoRepository<Tracker, String> {
    //db.getCollection('tracker').find({"data.payload.gps.speed": {$gt: "0.00"}, "data.payload.timeStamp": {$gte: "04/05/2017"}}).sort({$natural: -1}).limit(50);
    @Query("{'data.imei': {$gt: ?0}, 'data.payload.gps.timestamp': {$gte: ?1}}")
    public Collection<Tracker> findByImeiAndDate(String imei, DateTime timeStamp, Sort sort);

    @Query(value = "{'data._id': {$eq: ?0}, 'data.payload.gps.timestamp': {$gte: ?1, $lt: ?2} }")
    Page<Tracker> findLocationsByImeiAndDateBetween(String imei, DateTime dateFrom, DateTime dateTo, Pageable pageable);

    @Query(value = "{'data._id': {$eq: ?0}, 'data.payload.gps.timestamp': {$gte: ?1, $lt: ?2} }")
    public Collection<Tracker> findDriveStatesByImeiAndDateBetween(String imei, DateTime dateFrom, DateTime dateTo, Sort sort);
    
    @Query(value = "{'data._id': {$eq: ?0}, 'data.payload.gps.timestamp': {$gte: ?1, $lt: ?2} }")
    public Collection<Tracker> findTrackingUnitDataByImeiAndDateBetween(String imei, DateTime dateFrom, DateTime dateTo, Sort sort);
}
