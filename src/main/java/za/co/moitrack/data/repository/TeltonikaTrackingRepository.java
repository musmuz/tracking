package za.co.moitrack.data.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import za.co.moitrack.data.model.TeltonikaTracking;

public interface TeltonikaTrackingRepository extends MongoRepository<TeltonikaTracking, String> {
    TeltonikaTracking findByImei(long imei);
}
