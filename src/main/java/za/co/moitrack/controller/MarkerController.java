package za.co.moitrack.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import za.co.moitrack.data.model.Marker;
import za.co.moitrack.data.model.TeltonikaTracker;
import za.co.moitrack.data.model.TeltonikaTracking;
import za.co.moitrack.service.MarkerService;
import za.co.moitrack.service.TeltonikaTrackingService;

import java.util.Collection;
import java.util.List;

import static za.co.moitrack.controller.VehicleController.REGISTRATION;

/**
 * Created by nguni52 on 2/21/17.
 */
@RestController
@RequestMapping(value = MarkerController.MARKER)
public class MarkerController extends MainController {
    public static final String IMEIS = "imeis";
    public static final String MARKER = "marker";
    private static final String HOME_REGISTRATION = HOME + "{" + REGISTRATION + "}";
    private static final String FINDBY = "findby";
    private static final String HOME_FINDBY_IMEI = HOME + FINDBY + HOME + IMEI;
    public static final String TELTONIKA = "teltonika";
    public static final String HOME_TELTONIKA_FINDBY_IMEI = HOME + TELTONIKA + HOME + FINDBY + HOME + IMEI;
    public static final String HOME_TELTONIKA_LIST = HOME + TELTONIKA + HOME + LIST;

    @Autowired
    private MarkerService markerService;
    @Autowired
    private TeltonikaTrackingService teltonikaTrackingService;

    @GetMapping(HOME_LIST)
    public List<Marker> getMarkerData() {
        return markerService.getMarkers();
    }

    @GetMapping(HOME_REGISTRATION)
    public Marker getMarkerByVehicle(@PathVariable(REGISTRATION) String vehicleRegistration) {
        return markerService.findByVehicleRegistration(vehicleRegistration.toUpperCase());
    }

    @GetMapping(HOME_FINDBY_IMEI)
    public List<Marker> getVehicleMarker(@RequestParam(IMEIS) String[] imeis) {
        return markerService.findVehicleMarkers(imeis);
    }

    @GetMapping(HOME_TELTONIKA_FINDBY_IMEI)
    public Collection<TeltonikaTracker> findLatestTeltonikaTrackingsByImeis(@RequestParam(IMEIS) String[] imeis) {
        return teltonikaTrackingService.findLatestTrackings(imeis);
    }

    @GetMapping(HOME_TELTONIKA_LIST)
    public Collection<TeltonikaTracking> findLatestTeltonikaTrackings() {
        return teltonikaTrackingService.findTrackings();
    }
}


