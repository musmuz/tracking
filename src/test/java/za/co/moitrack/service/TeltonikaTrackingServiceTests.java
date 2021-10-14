package za.co.moitrack.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import za.co.moitrack.DemoApplicationTests;
import za.co.moitrack.data.builder.TeltonikaTrackerBuilder;
import za.co.moitrack.data.builder.TeltonikaTrackingBuilder;
import za.co.moitrack.data.model.TeltonikaTracker;
import za.co.moitrack.data.model.TeltonikaTracking;
import za.co.moitrack.data.repository.TeltonikaTrackerRepository;
import za.co.moitrack.data.repository.TeltonikaTrackingRepository;

import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TeltonikaTrackingServiceTests  extends DemoApplicationTests {
    @Autowired
    private TeltonikaTrackerRepository teltonikaTrackerRepository;
    @Autowired
    private TeltonikaTrackingRepository teltonikaTrackingRepository;
    @Autowired
    private TeltonikaTrackingService teltonikaTrackingService;

    private List<TeltonikaTracking> teltonikaTrackings;
    private TeltonikaTracking tracking;

    private List<TeltonikaTracker> teltonikaTrackers;
    private TeltonikaTracker tracker;

    @Before
    public void setUp() {
        teltonikaTrackings = TeltonikaTrackingBuilder.buiidTrackings(2);
        tracking = TeltonikaTrackingBuilder.BuildATracking(1);

        teltonikaTrackers = TeltonikaTrackerBuilder.buildTrackings(2);
        tracker = TeltonikaTrackerBuilder.BuildATracker(1);
    }

    @After
    public void tearDown() {
        teltonikaTrackerRepository.deleteAll();
    }

    @Test
    public void createTeltonikaTracker() {
        teltonikaTrackingService.create(tracking);
        TeltonikaTracking newTracking = teltonikaTrackingService.findOneByImei(tracking.getImei());
        assertEquals(newTracking.getImei(), tracking.getImei());
    }

    @Test
    public void testFindAllTeltonikaTracker() {
        teltonikaTrackerRepository.save(tracker);
        Collection<TeltonikaTracker> odometerByImei = teltonikaTrackingService.findTrackersByImei(Long.valueOf(tracker.getImei()));
        assertEquals(1, odometerByImei.size());

    }

    @Test
    public void testLatestTrackerList() {
        teltonikaTrackerRepository.save(teltonikaTrackers);
        String[] imeis = {"12345550", "12345551"};
        Collection<TeltonikaTracker> latestTrackers = teltonikaTrackingService.findLatestTrackings(imeis);
        assertEquals(2, latestTrackers.size());
    }
}
