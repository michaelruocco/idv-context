package uk.co.idv.context.adapter.json.lockout.attempt;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import uk.co.idv.context.entities.lockout.attempt.Attempt;
import uk.co.idv.context.entities.lockout.attempt.Attempts;

import java.io.IOException;

public class VerificationAttemptsSerializer extends StdSerializer<Attempts> {

    public VerificationAttemptsSerializer() {
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
