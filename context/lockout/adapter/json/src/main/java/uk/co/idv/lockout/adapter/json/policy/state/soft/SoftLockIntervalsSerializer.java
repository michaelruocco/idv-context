package uk.co.idv.lockout.adapter.json.policy.state.soft;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import uk.co.idv.lockout.entities.policy.soft.SoftLockInterval;
import uk.co.idv.lockout.entities.policy.soft.SoftLockIntervals;

import java.io.IOException;

public class SoftLockIntervalsSerializer extends StdSerializer<SoftLockIntervals> {

    public SoftLockIntervalsSerializer() {
        super(SoftLockIntervals.class);
    }

    @Override
    public void serialize(SoftLockIntervals intervals, JsonGenerator json, SerializerProvider provider) throws IOException {
        json.writeStartArray();
        for (SoftLockInterval interval : intervals.values()) {
            provider.defaultSerializeValue(interval, json);
        }
        json.writeEndArray();
    }

}
