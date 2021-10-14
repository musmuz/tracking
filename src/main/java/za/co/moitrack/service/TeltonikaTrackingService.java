package za.co.moitrack.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import za.co.moitrack.data.model.TeltonikaTracker;
import za.co.moitrack.data.model.TeltonikaTracking;
import za.co.moitrack.data.repository.TeltonikaTrackerRepository;
import za.co.moitrack.data.repository.TeltonikaTrackingRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class TeltonikaTrackingService {
    private Log log = LogFactory.getLog(this.getClass().getName());
    @Autowired
    private TeltonikaTrackerRepository teltonikaTrackerRepository;
    @Autowired
    private TeltonikaTrackingRepository teltonikaTrackingRepository;


    public TeltonikaTracking create(TeltonikaTracking teltonikaTracking) {
        return teltonikaTrackingRepository.save(teltonikaTracking);
    }


    public Collection<TeltonikaTracker> findTrackersByImei(long imei) {
        return teltonikaTrackerRepository.findTeltonikaTrackersByImei(imei);
    }

    public TeltonikaTracker findOne(long imei) {
        Sort sort = new Sort(Sort.Direction.DESC, "timestamp");
        return teltonikaTrackerRepository.findByImei(imei, sort);

    }

    public Collection<TeltonikaTracker> findLatestTrackings(String[] imeis) {
        List<TeltonikaTracker> trackings = new ArrayList<>();
        for (String imei : imeis) {
            try {
                TeltonikaTracker tracking = findOne(Long.parseLong(imei));
                trackings.add(tracking);
            } catch (Exception ex) {
                log.info("Error retrieving teltonika trackings");
                log.debug(ex);
            }
        }
        return trackings;
    }

    public TeltonikaTracking findOneByImei(long imei) {
        return teltonikaTrackingRepository.findByImei(imei);
    }

    public Collection<TeltonikaTracking> findTrackings() {
        return teltonikaTrackingRepository.findAll();
    }
}
