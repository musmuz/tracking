package za.co.moitrack.data.builder;

import org.joda.time.DateTime;
import za.co.moitrack.data.model.DriveState;
import za.co.moitrack.data.model.Marker;

import java.util.ArrayList;
import java.util.List;

public class MarkerBuilder {
    private String imei;
    private Float latitude;
    private Float longitude;
    private String description;
    private boolean draggable;
    private String timeStamp;
    private String callInterface;
    private String speed;
    private double odometer;
    private Float heading;
    private String tagging;
    private String driverBehaviour;
    private DriveState driveState;

    public MarkerBuilder(String imei, Float latitude, Float longitude, String description, boolean draggable,
                         String timeStamp, String callInterface, String speed, double odometer, Float heading, String tagging, String driverBehaviour, DriveState driveState) {
        this.imei = imei;
        this.latitude = latitude;
        this.longitude = longitude;
        this.description = description;
        this.draggable = draggable;
        this.timeStamp = timeStamp;
        this.callInterface = callInterface;
        this.speed = speed;
        this.odometer = odometer;
        this.heading = heading;
        this.tagging = tagging;
        this.driverBehaviour = driverBehaviour;
        this.driveState = driveState;
    }

    public static Marker BuildAMarker(int i) {
        String imei = "1234555" + i;
        Float latitude = (float) (-23.00924 + i);
        Float longitude = (float) -23.00924 + i;
        String description = "Description " + i;
        boolean draggable = false;
        String timeStamp = DateTime.now().toString();
        String callInterface = "callInterface" + i;
        String speed = "speed" + i;
        double odometer = 8585 + i;
        Float heading = (float) (60.000 + i);
        String tagging = "0000000" + i;
        String driverBehaviour = "B" + i;
        DriveState driveState = DriveStateBuilder.buildADriveState(1);

        return new MarkerBuilder(imei, latitude, longitude, description, draggable, timeStamp, callInterface, speed, odometer, heading, tagging, driverBehaviour, driveState).build();
    }

    private Marker build() {
        return new Marker(imei, latitude, longitude, description, draggable, timeStamp, callInterface, speed, odometer, heading, tagging, driverBehaviour, driveState);
    }

    public static List<Marker> buildMarkers(int i) {
        List<Marker> markers = new ArrayList<>();

        for (int j = 0; j < i; j++) {
            markers.add(BuildAMarker(i));
        }

        return markers;
    }
}
