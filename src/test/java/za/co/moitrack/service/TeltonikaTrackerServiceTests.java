package za.co.moitrack.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import za.co.moitrack.DemoApplicationTests;
import za.co.moitrack.data.builder.TeltonikaTrackerBuilder;
import za.co.moitrack.data.model.DriveState;
import za.co.moitrack.data.model.TeltonikaTracker;
import za.co.moitrack.data.repository.TeltonikaTrackerRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TeltonikaTrackerServiceTests extends DemoApplicationTests {
    private Log log = LogFactory.getLog(this.getClass().getName());

    @Autowired
    private TeltonikaTrackerRepository teltonikaTrackerRepository;
    @Autowired
    private TeltonikaTrackerService teltonikaTrackerService;

    private List<TeltonikaTracker> trackers;
    private TeltonikaTracker tracker;

    private List<DriveState> driveStates;
    private DriveState driveState;

    @Before
    public void setUp() {
        trackers = TeltonikaTrackerBuilder.buildTrackings(5);
        tracker = TeltonikaTrackerBuilder.BuildATracker(1);

    }

    @After
    public void tearDown() {
        teltonikaTrackerRepository.deleteAll();
    }

    @Test
    public void testTripListByImeiAndDateRange() {
        teltonikaTrackerRepository.save(tracker);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        long imei = 1234556;
        String dateFrom = LocalDate.now().minusDays(1).format(formatter);
        String dateTo = LocalDate.now().plusDays(1).format(formatter);
        List<List<TeltonikaTracker>> trackers = teltonikaTrackerService.findTripListByImeiAndDate(imei, dateFrom, dateTo);

        log.info("Tracker size is: " + trackers.size());

        log.info("testByImeiAndDateRange ENDED\n\n\n");
    }
}
