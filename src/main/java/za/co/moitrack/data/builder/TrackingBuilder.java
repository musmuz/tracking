package za.co.moitrack.data.builder;

import za.co.moitrack.data.model.Payload;
import za.co.moitrack.data.model.Tracking;
import za.co.moitrack.data.model.Vehicle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nguni52 on 2017/06/08.
 */
public class TrackingBuilder {
    private String imei;
    private Payload payload;
    private String ip;
    private String callInterface;
    private Vehicle vehicle;

    public TrackingBuilder(String imei, Payload payload, String ip, String callInterface, Vehicle vehicle) {
        this.imei = imei;
        this.payload = payload;
        this.ip = ip;
        this.callInterface = callInterface;
        this.vehicle = vehicle;
    }

    public static Tracking BuildATracking(int i) {
        String imei = "1234555" + i;
        Payload payload = PayloadBuilder.buildAPayload(i);
        String ip = "ip" + i;
        String callInterface = "callInterface" + i;
        Vehicle vehicle = VehicleBuilder.buildAVehicle(i);
        return new TrackingBuilder(imei, payload, ip, callInterface, vehicle).build();
    }

    private Tracking build() {
        return new Tracking(imei, payload, ip, callInterface, vehicle);
    }

    public static List<Tracking> buildTrackings(int i) {
        List<Tracking> trackings = new ArrayList<>();

        for (int j = 0; j < i; j++) {
            trackings.add(BuildATracking(i));
        }
        return trackings;
    }

    public static Tracking buildATracking(int i, String imei) {
        Payload payload = PayloadBuilder.buildAPayload(i);
        String ip = "ip" + i;
        String callInterface = "callInterface" + i;
        Vehicle vehicle = VehicleBuilder.buildAVehicle(i);
        return new TrackingBuilder(imei, payload, ip, callInterface, vehicle).build();
    }
}
