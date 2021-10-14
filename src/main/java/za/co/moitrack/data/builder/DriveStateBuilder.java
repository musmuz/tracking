package za.co.moitrack.data.builder;

import za.co.moitrack.data.model.DriveState;

/**
 * Created by nguni52 on 2017/06/08.
 */
public class DriveStateBuilder {
    private String batteryVoltage;
    private String engineRuntime;
    private String ignitionState;

    public DriveStateBuilder(String batteryVoltage, String engineRuntime, String ignitionState) {
        this.batteryVoltage = batteryVoltage;
        this.engineRuntime = engineRuntime;
        this.ignitionState = ignitionState;
    }

    public static DriveState buildADriveState(int i) {
        String batteryVoltage = "batteryVoltage" + i;
        String engineRuntime = "engineRuntime"+i;
        String ignitionState = (i%2 == 0)? "true":"false";

        return new DriveStateBuilder(batteryVoltage, engineRuntime, ignitionState).build();
    }

    private DriveState build() {
        return new DriveState(batteryVoltage, engineRuntime, ignitionState);
    }
}
