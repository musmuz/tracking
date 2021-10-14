package za.co.moitrack.data.builder;

import org.joda.time.DateTime;
import za.co.moitrack.data.model.GPS;


/**
 * Created by nguni52 on 2017/06/08.
 */
public class GPSBuilder {
    private String satellites;
    private String hdop;
    private String latitude;
    private DateTime timestamp;
    private String altitude;
    private String speed;
    private String heading;
    private String longitude;

    public GPSBuilder(String satellites, String hdop, String latitude, DateTime timestamp, String altitude,
                      String speed, String heading, String longitude) {
        this.satellites = satellites;
        this.hdop = hdop;
        this.latitude = latitude;
        this.timestamp = timestamp;
        this.altitude = altitude;
        this.speed = speed;
        this.heading = heading;
        this.longitude = longitude;
    }

    public static GPS buildAGPS(int i) {
        String satellites = "satelites"+i;
        String hdop = "hdop" +i;
        String latitude = "" + (-23.00924+i);
        DateTime timestamp = DateTime.now();
        String altitude = "" + (1234.234) + i;
        String speed = "speed" + i;
        String heading = "" + (60.2222+i);
        String longitude = "" + (-23.00924+i);

        return new GPSBuilder(satellites, hdop, latitude, timestamp, altitude, speed, heading, longitude).build();
    }

    private GPS build() {
        return new GPS(satellites, hdop, latitude, timestamp, altitude, speed, heading, longitude);
    }
}
