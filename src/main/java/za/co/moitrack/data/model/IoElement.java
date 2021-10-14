package za.co.moitrack.data.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IoElement {
    private int id;
    private int value;
    private String label;
    private String valueHuman;
}
