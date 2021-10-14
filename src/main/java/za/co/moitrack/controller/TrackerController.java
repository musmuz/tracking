package za.co.moitrack.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import za.co.moitrack.data.model.DriveState;
import za.co.moitrack.data.model.Location;
import za.co.moitrack.data.model.TeltonikaTracker;
import za.co.moitrack.data.model.Tracker;
import za.co.moitrack.service.TeltonikaTrackerService;
import za.co.moitrack.service.TrackerService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by nguni52 on 2017/04/05.
 */
@RestController
@RequestMapping(value = TrackerController.TRACKER)
public class TrackerController extends MainController {
    public static final String TRACKER = "tracker";
    private static final String PATH = "path";
    public static final String HOME_PATH = HOME + PATH;
    private static final String TRIPLIST = "triplist";
    public static final String HOME_TRIPLIST = HOME + TRIPLIST;
    private static final String DATE_FROM = "dateFrom";
    private static final String DATE_TO = "dateTo";
    private static final String LIMIT_OUTPUT = "limitOutput";
    private static final String DRIVESTATE = "drivestate";
    private static final String HOME_DRIVESTATE = HOME + DRIVESTATE;
    public static final String TELTONIKA = "teltonika";
    public static final String HOME_TELTONIKA_TRIPLIST = HOME + TELTONIKA + HOME + TRIPLIST;

    @Autowired
    private TrackerService trackerService;
    @Autowired
    private TeltonikaTrackerService teltonikaTrackerService;

    @GetMapping
    public Collection<Tracker> findByImeiAndDate(@RequestParam(IMEI) String imei,
                                                 @RequestParam(DATE_FROM) String dateFrom,
                                                 @RequestParam(DATE_TO) String dateTo) {
        return trackerService.findByImeiAndDate(imei, dateFrom, dateTo);
    }

    @GetMapping(HOME_DRIVESTATE)
    public List<DriveState> findDrivestatesByImeiAndDate(@RequestParam(IMEI) String imei,
                                                         @RequestParam(DATE_FROM) String dateFrom,
                                                         @RequestParam(DATE_TO) String dateTo) {
        try {
            return trackerService.findDriveStatesByImeiAndDate(imei, dateFrom, dateTo);

        } catch (Exception ex) {
            return new ArrayList<>();
        }
    }

    @GetMapping(HOME_PATH)
    public List<Location> findLocationsByImeiAndDate(@RequestParam(IMEI) String imei,
                                                     @RequestParam(DATE_FROM) String dateFrom,
                                                     @RequestParam(DATE_TO) String dateTo,
                                                     @RequestParam(LIMIT_OUTPUT) Boolean limitOutput) {
        try {
            return trackerService.findLocationsByImeiAndDate(imei, dateFrom, dateTo, limitOutput);
        } catch (Exception ex) {
            return new ArrayList<>();
        }
    }

    @GetMapping(HOME_LIST)
    public List<Tracker> getByAllImeiAndDateRange(@RequestParam(IMEI) String imei,
                                                  @RequestParam(DATE_FROM) String dateFrom,
                                                  @RequestParam(DATE_TO) String dateTo) {
        try {
            return trackerService.findAllLocationsByImeiAndDate(imei, dateFrom, dateTo);
        } catch (Exception ex) {
            return new ArrayList<>();
        }
    }

    @GetMapping(HOME_TRIPLIST)
    public List<List<Tracker>> getByAllTripListImeiAndDateRange(@RequestParam(IMEI) String imei,
                                                                @RequestParam(DATE_FROM) String dateFrom,
                                                                @RequestParam(DATE_TO) String dateTo) {
        try {
            return trackerService.FindTripListByImeiAndDate(imei, dateFrom, dateTo);
        } catch (Exception ex) {
            return new ArrayList<>();
        }
    }

    @GetMapping(HOME_TELTONIKA_TRIPLIST)
    public List<List<TeltonikaTracker>> getTeltonikaTripListByImeiAndDateRange(@RequestParam(IMEI) long imei,
                                                                               @RequestParam(DATE_FROM) String dateFrom,
                                                                               @RequestParam(DATE_TO) String dateTo) {
        try {
            return teltonikaTrackerService.findTripListByImeiAndDate(imei, dateFrom, dateTo);
        } catch (Exception ex) {
            return new ArrayList<>();
        }
    }
}
