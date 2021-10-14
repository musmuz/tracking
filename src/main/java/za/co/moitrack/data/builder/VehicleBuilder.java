package za.co.moitrack.data.builder;

import za.co.moitrack.data.model.Vehicle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nguni52 on 2017/03/24.
 */
public class VehicleBuilder {
    private String id;
    private String name;
    private String model;
    private String imei;
    private String registrationNumber;
    private String description;
    private String key;



    public VehicleBuilder(String id, String name, String model, String imei, String registrationNumber, String description, String key) {
        this.id = id;
        this.name = name;
        this.model = model;
        this.imei  = imei;
        this.registrationNumber = registrationNumber;
        this.description = description;
        this.key = key;

    }

    public static Vehicle buildAVehicle(int i) {
        String id = "id" + i;
        String name = "vehicle " + i;
        String model = "model " + i;
        String imei = "1234555" + (i%10);
        String registrationNumber = "CV" + (i%100) + "RLGP";
        String description = "Description " + i;
        String key = "1234098213408" + (i%10);

        
        return new VehicleBuilder(id, name, model, imei, registrationNumber, description, key).build();
    }

    private Vehicle build() {
        Vehicle vehicle = new Vehicle();
        vehicle.setId(id);
        vehicle.setName(name);
        vehicle.setModel(model);
        vehicle.setImei(imei);
        vehicle.setRegistrationNumber(registrationNumber);
        vehicle.setDescription(description);
        vehicle.setKey(key);


        return vehicle;
    }

    public static List<Vehicle> buildVehicles(int i) {
        List<Vehicle> vehicles = new ArrayList<>();

        for(int j = 0;j < i;j++){
            vehicles.add(buildAVehicle(j));

        }
        return vehicles;
    }


}
