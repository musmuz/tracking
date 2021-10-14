package za.co.moitrack.data.builder;

import org.joda.time.DateTime;
import za.co.moitrack.data.model.IoElement;
import za.co.moitrack.data.model.TeltonikaGps;
import za.co.moitrack.data.model.TeltonikaTracker;

import java.util.ArrayList;
import java.util.List;

public class TeltonikaTrackerBuilder {
    private long imei;
    private DateTime timestamp;
    private TeltonikaGps gps;
    private int priority;
    private int event_id;
    private int properties_count;
    private List<IoElement> ioElements;

    public TeltonikaTrackerBuilder(long imei, DateTime timestamp, TeltonikaGps gps, int priority, int event_id, int properties_count, List<IoElement> ioElements) {
        this.imei = imei;
        this.timestamp = timestamp;
        this.gps = gps;
        this.priority = priority;
        this.event_id = event_id;
        this.properties_count = properties_count;
        this.ioElements = ioElements;
    }

    public static TeltonikaTracker BuildATracker(int i) {
        long imei = 1234555 + i;
        DateTime timestamp = new DateTime();
        TeltonikaGps gps = TeltonikaGPSBuilder.buildAGPS(i);
        int priority = 0 + i;
        int event_id = 0 + i;
        int properties_count = 12 + i;
        List<IoElement> ioElements = new ArrayList<>();

        return new TeltonikaTrackerBuilder(imei, timestamp, gps, priority, event_id, properties_count, ioElements).build();
    }

    private TeltonikaTracker build() {
        return new TeltonikaTracker(imei, timestamp, gps, priority, event_id, properties_count, ioElements);
    }

    public static List<TeltonikaTracker> buildTrackings(int i) {
        List<TeltonikaTracker> trackers = new ArrayList<>();

        for (int j = 0; j < i; j++) {
            trackers.add(BuildATracker(i));
        }
        return trackers;
    }


}
