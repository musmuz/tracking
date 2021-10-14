package za.co.moitrack.data.builder;

import org.joda.time.DateTime;
import za.co.moitrack.data.model.Odometer;

import java.util.ArrayList;
import java.util.List;

public class OdometerBuilder {
    private String id;
    private String imei;
    private DateTime timeStamp;
    private double actualOdometer;
    private double zeroOdometer;
    private String alertFlag;


    public OdometerBuilder(String id, String imei, DateTime timeStamp, double actualOdometer, double zeroOdometer, String alertFlag) {
        this.id = id;
        this.imei = imei;
        this.timeStamp = timeStamp;
        this.actualOdometer = actualOdometer;
        this.zeroOdometer = zeroOdometer;
        this.alertFlag = alertFlag;

    }

    public static Odometer buildAnOdometer(int i) {
        String id = "id" + i;
        String imei = "1234555" + i;
        DateTime timeStamp = DateTime.now();
        double actualOdometer = 15000 + (10 * i);
        double zeroOdometer = 200 + (5 * i);
        String alertFlag = "false";

        return new OdometerBuilder(id, imei, timeStamp, actualOdometer, zeroOdometer, alertFlag).build();
    }

    private Odometer build() {
        Odometer odometer = new Odometer();
        odometer.setId(id);
        odometer.setImei(imei);
        odometer.setTimeStamp(timeStamp);
        odometer.setActualOdometer(actualOdometer);
        odometer.setZeroOdometer(zeroOdometer);
        odometer.setAlertFlag(alertFlag);
        
        return odometer;
    }

    public static List<Odometer> buildOdometers(int i) {
        List<Odometer> odometers = new ArrayList<>();

        for(int j = 0; j < i; j++){
            odometers.add(buildAnOdometer(j));
        }
        return odometers;

    }
}
