package za.co.moitrack.data.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by nguni52 on 12/2/16.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DriveState {
    private String batteryVoltage;
    private String engineRuntime;
    private String ignitionState;
}
