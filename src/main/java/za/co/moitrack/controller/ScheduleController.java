package za.co.moitrack.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import za.co.moitrack.scheduler.ScheduledTasks;

@RestController
@RequestMapping(ScheduleController.REPORT)
public class ScheduleController extends MainController {
    public static final String REPORT = "report";
    private static final String MORNING_VEHICLE_REPORT = "morning-vehicle-report";
    private static final String HOME_MORNING_VEHICLE_REPORT = HOME + MORNING_VEHICLE_REPORT;
    private static final String EVENING_VEHICLE_REPORT = "evening-vehicle-report";
    private static final String HOME_EVENING_VEHICLE_REPORT = HOME + EVENING_VEHICLE_REPORT;

    @Autowired
    private ScheduledTasks scheduledTasks;

    @GetMapping(ScheduleController.HOME_MORNING_VEHICLE_REPORT)
    public void scheduleMorningVehicleTripRecords() {
        scheduledTasks.scheduleMorningVehicleTripRecords();
    }

    @GetMapping(ScheduleController.HOME_EVENING_VEHICLE_REPORT)
    public void scheduleAfternoonVehicleTripRecords() {
        scheduledTasks.scheduleAfternoonVehicleTripRecords();
    }
}
