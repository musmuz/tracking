package za.co.moitrack.data.builder;

import za.co.moitrack.data.model.TeltonikaGps;

public class TeltonikaGPSBuilder {
    private int satellites;
    private double latitude;
    private int altitude;
    private int speed;
    private double angle;
    private double longitude;

    public TeltonikaGPSBuilder(int satellites, double latitude, int altitude, int speed, double angle, double longitude) {
        this.satellites = satellites;
        this.latitude = latitude;
        this.altitude = altitude;
        this.speed = speed;
        this.angle = angle;
        this.longitude = longitude;
    }

    public static TeltonikaGps buildAGPS(int i) {
        int satellites = i;
        double latitude = -23.00924 +i;
        int altitude = 1234 + i;
        int speed =  i;
        double angle = 60.2222+i;
        double longitude = 23.00924+i;

        return new TeltonikaGPSBuilder(satellites, latitude, altitude, speed, angle, longitude).build();
    }

    private TeltonikaGps build() {
        return new TeltonikaGps(satellites, latitude, altitude, speed, angle, longitude);
    }
}

