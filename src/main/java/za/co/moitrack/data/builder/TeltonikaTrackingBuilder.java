package za.co.moitrack.data.builder;

import org.joda.time.DateTime;
import za.co.moitrack.data.model.IoElement;
import za.co.moitrack.data.model.TeltonikaGps;
import za.co.moitrack.data.model.TeltonikaTracking;

import java.util.ArrayList;
import java.util.List;

public class TeltonikaTrackingBuilder {
    private long imei;
    private DateTime timestamp;
    private TeltonikaGps gps;
    private int priority;
    private int event_id;
    private int properties_count;
    private List<IoElement> ioElements;

    public TeltonikaTrackingBuilder(long imei, DateTime timestamp, TeltonikaGps gps, int priority, int event_id, int properties_count, List<IoElement> ioElements) {
        this.imei = imei;
        this.timestamp = timestamp;
        this.gps = gps;
        this.priority = priority;
        this.event_id = event_id;
        this.properties_count = properties_count;
        this.ioElements = ioElements;
    }

    public static TeltonikaTracking BuildATracking(int i) {
        long imei = 1234555 + i;
        DateTime timestamp = new DateTime();
        TeltonikaGps gps = new TeltonikaGps();
        int priority = 0 + i;
        int event_id = 0 + i;
        int properties_count = 12 + i;
        List<IoElement> ioElements = new ArrayList<>();

        return new TeltonikaTrackingBuilder(imei, timestamp, gps, priority, event_id, properties_count, ioElements).build();
    }

    private TeltonikaTracking build() {
        return new TeltonikaTracking(imei, timestamp, gps, priority, event_id, properties_count, ioElements);
    }

    public static List<TeltonikaTracking> buiidTrackings(int i) {
        List<TeltonikaTracking> trackings = new ArrayList<>();

        for (int j = 0; j < i; j++) {
            trackings.add(BuildATracking(i));
        }
        return trackings;
    }


}
