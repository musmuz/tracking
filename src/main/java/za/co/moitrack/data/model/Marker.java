package za.co.moitrack.data.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;


/**
 * Created by nguni52 on 2/21/17.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Marker {
    private String imei;
    private float lat;
    private float lng;
    private String label;
    private boolean draggable;
    @DateTimeFormat(iso =  DateTimeFormat.ISO.DATE_TIME)
    private String timeStamp;
    private String transmission;
    private String speed;
    private double odometer;
    private float heading;
    private String tagging;
    private String driverBehaviour;
    private DriveState driveStates;

}
