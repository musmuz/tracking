package za.co.moitrack.data.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;
import org.springframework.format.annotation.DateTimeFormat;
import za.co.moitrack.config.LocalDateDeserializer;
import za.co.moitrack.config.LocalDateSerializer;


/**
 * Created by nguni52 on 12/2/16.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payload {
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private DateTime timeStamp;
    private DriveState driveStates;
    private GPS gps;
    private double odometerReading;
    private String tagging;
    private String driverBehaviour;
}



