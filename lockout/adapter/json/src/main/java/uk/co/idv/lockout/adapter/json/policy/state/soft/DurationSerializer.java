package uk.co.idv.lockout.adapter.json.policy.state.soft;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.time.Duration;

public class DurationSerializer extends StdSerializer<Duration> {

    public DurationSerializer() {
        super(Duration.class);
    }

    @Override
    public void serialize(Duration duration, JsonGenerator json, SerializerProvider provider) throws IOException {
        json.writeNumber(duration.toMillis());
    }

}
