package za.co.moitrack.data.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeltonikaGps {
    private int satellites;
    private double latitude;
    private int altitude;
    private int speed;
    private double angle;
    private double longitude;
}
