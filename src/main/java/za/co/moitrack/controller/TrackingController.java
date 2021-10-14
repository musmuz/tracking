package za.co.moitrack.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import za.co.moitrack.data.model.TeltonikaTracking;
import za.co.moitrack.data.model.Tracking;
import za.co.moitrack.service.TeltonikaTrackingService;
import za.co.moitrack.service.TrackingService;

import java.util.Collection;

/**
 * Created by nguni52 on 2/20/17.
 */
@RestController
@RequestMapping(value = TrackingController.TRACKING)
public class TrackingController extends MainController {
    public static final String TRACKING = "tracking";
    public static final String HOME_IMEI = HOME + "{" + IMEI + "}";
    public static final String REGISTRATION = "registration";
    private static final String FINDBY = "findby";
    public static final String HOME_FINDBY_REGISTRATION = HOME + FINDBY + HOME + REGISTRATION;
    public static final String TELTONIKA = "teltonika";
    public static final String HOME_TELTONIKA_CREATE = HOME + TELTONIKA + HOME + CREATE;

    @Autowired
    private TrackingService trackingService;
    @Autowired
    private TeltonikaTrackingService teltonikaTrackingService;
    private Log log = LogFactory.getLog(this.getClass().getName());

    @PostMapping(HOME_CREATE)
    public Tracking createLatestTrackingDataForVehicle(@RequestBody Tracking tracking) {
        log.debug("Tracking data is: " + tracking.toString());
        return trackingService.create(tracking);
    }

    @PostMapping(HOME_TELTONIKA_CREATE)
    public TeltonikaTracking createLatestTeltonikaTrackingDataForVehicle(@RequestBody TeltonikaTracking teltonikaTracking) {
        return teltonikaTrackingService.create(teltonikaTracking);
    }

    @GetMapping(HOME_IMEI)
    public Tracking findOne(@PathVariable(IMEI) String imei) {
        return trackingService.findOne(imei);
    }

    @GetMapping(HOME_LIST)
    public Collection<Tracking> findAllTrackingDataForVehicles() {
        return trackingService.findAll();
    }

    @GetMapping(HOME_FINDBY_REGISTRATION)
    public Tracking findByRegistration(@RequestParam(REGISTRATION) String registrationNumber) {
        return trackingService.findByVehicleRegistration(registrationNumber);
    }
}
