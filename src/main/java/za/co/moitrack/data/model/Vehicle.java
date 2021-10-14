package za.co.moitrack.data.model;

import lombok.Data;
import org.springframework.data.annotation.Id;


/**
 * Created by nguni52 on 2/20/17.
 */
@Data
public class Vehicle {
    @Id
    private String id;
    private String name;
    private String model;
    private String imei;
    private String registrationNumber;
    private String description;
    private String key;

}
