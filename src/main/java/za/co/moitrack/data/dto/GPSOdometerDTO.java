package za.co.moitrack.data.dto;

import lombok.Data;
import za.co.moitrack.data.model.Marker;

/**
 * Created by nguni52 on 2017/08/25.
 */
@Data
public class GPSOdometerDTO {
    private Marker marker;
    private float odometer;
    private String registrationNumber;
}
