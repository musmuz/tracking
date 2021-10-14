package za.co.moitrack.data.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by nguni52 on 2017/04/05.
 */
@Data
@NoArgsConstructor
@Document
@AllArgsConstructor
public class Tracker {
    @Id
    private String imei;
    private Tracking data;
    private String topic;
    private String retain;
    private int qos;
}
