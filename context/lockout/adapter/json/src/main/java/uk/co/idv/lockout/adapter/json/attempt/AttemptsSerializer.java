package uk.co.idv.lockout.adapter.json.attempt;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import uk.co.idv.lockout.entities.attempt.Attempt;
import uk.co.idv.lockout.entities.attempt.Attempts;

import java.io.IOException;

public class AttemptsSerializer extends StdSerializer<Attempts> {

    public AttemptsSerializer() {
        super(Attempts.class);
    }

    @Override
    public void serialize(Attempts attempts, JsonGenerator json, SerializerProvider provider) throws IOException {
        json.writeStartObject();
        json.writeStringField("id", attempts.getId().toString());
        json.writeStringField("idvId", attempts.getIdvId().getValue());
        json.writeArrayFieldStart("attempts");
        for (Attempt attempt : attempts) {
            provider.defaultSerializeValue(attempt, json);
        }
        json.writeEndArray();
        json.writeEndObject();
    }

}
