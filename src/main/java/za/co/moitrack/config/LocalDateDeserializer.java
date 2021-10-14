package za.co.moitrack.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.joda.time.DateTime;

import java.io.IOException;

import static za.co.moitrack.MoitrackApplication.FORMATTER;

public class  LocalDateDeserializer extends JsonDeserializer<DateTime> {

    @Override
    public DateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        return DateTime.parse(p.getValueAsString(), FORMATTER);
    }
}
