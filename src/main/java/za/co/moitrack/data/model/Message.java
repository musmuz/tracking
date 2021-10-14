package za.co.moitrack.data.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by nguni52 on 12/2/16.
 */
@Data
@NoArgsConstructor
public class Message {
    //    private String interface;
    private String imei;
    private Payload payload;
    private String ip;
    private String callInterface;
}
