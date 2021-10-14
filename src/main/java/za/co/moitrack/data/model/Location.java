package za.co.moitrack.data.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by nguni52 on 2016/10/24.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Location {
    private double latitude;
    private double longitude;
}
