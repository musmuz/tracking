package za.co.moitrack.data.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by nguni52 on 2/20/17.
 */
@Data
@Document
@NoArgsConstructor
@AllArgsConstructor
public class Tracking {
    @Id
    private String imei;
    private Payload payload;
    private String ip;
    private String callInterface;
    private Vehicle vehicle;
}
