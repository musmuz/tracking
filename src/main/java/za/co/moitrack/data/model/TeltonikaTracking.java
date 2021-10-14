package za.co.moitrack.data.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;
import za.co.moitrack.config.LocalDateDeserializer;
import za.co.moitrack.config.LocalDateSerializer;

import java.util.List;

@Data
@Document(collection = "teltonikatrackings")
@NoArgsConstructor
@AllArgsConstructor
public class TeltonikaTracking {
    @Id
    private long imei;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private DateTime timestamp;
    private TeltonikaGps gps;
    private int priority;
    private int event_id;
    private int properties_count;
    private List<IoElement> ioElements;
}
