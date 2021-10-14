package za.co.moitrack.data.builder;

import za.co.moitrack.data.model.Tracker;
import za.co.moitrack.data.model.Tracking;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nguni52 on 2017/06/08.
 */
public class TrackerBuilder {
    private String imei;
    private Tracking data;
    private String topic;
    private String retain;
    private int qos;

    public TrackerBuilder(String imei, Tracking data, String topic, String retain, int qos) {
        this.imei = imei;
        this.data = data;
        this.topic = topic;
        this.retain = retain;
        this.qos = qos;
    }

    public static Tracker buildATracker(int i) {
        String imei = "1234555" + i;
        Tracking data = TrackingBuilder.BuildATracking(i);
        String topic = "topic" + i;
        String retain = "retain" + i;
        int qos = i%2;

        return new TrackerBuilder(imei, data, topic, retain, qos).build();
    }

    private Tracker build() {
        return new Tracker(imei, data, topic, retain, qos);
    }

    public static List<Tracker> buildTrackers(int i) {
        List<Tracker> trackers = new ArrayList<>();

        for(int j = 0;j < i;j++) {
            trackers.add(buildATracker(j));
        }
        return trackers;
    }
}
