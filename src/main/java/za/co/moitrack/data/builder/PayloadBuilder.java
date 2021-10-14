package za.co.moitrack.data.builder;

import org.joda.time.DateTime;
import za.co.moitrack.data.model.DriveState;
import za.co.moitrack.data.model.GPS;
import za.co.moitrack.data.model.Payload;


/**
 * Created by nguni52 on 2017/06/08.
 */
public class PayloadBuilder {
    private DateTime timeStamp;
    private DriveState driveStates;
    private GPS gps;
    private  double odometerReading;
    private String tagging;
    private String driverBehaviour;
    public PayloadBuilder(DateTime timeStamp, DriveState driveStates, GPS gps, double odometerReading, String tagging, String driverBehaviour) {
        this.timeStamp = timeStamp;
        this.driveStates = driveStates;
        this.gps = gps;
        this.odometerReading = odometerReading;
        this.tagging = tagging;
        this.driverBehaviour = driverBehaviour;
    }

    public static Payload buildAPayload(int i) {
        DateTime timeStamp = DateTime.now();
        DriveState driveStates = DriveStateBuilder.buildADriveState(i);
        GPS gps = GPSBuilder.buildAGPS(i);
        double odometerReading = 12300.0;
        String tagging = "000000" + i;
        String driverBehaviour = "B" + i;


        return new PayloadBuilder(timeStamp, driveStates, gps, odometerReading, tagging, driverBehaviour).build();
    }

    private Payload build() {
        return new Payload(timeStamp, driveStates, gps, odometerReading, tagging, driverBehaviour);
    }
}
