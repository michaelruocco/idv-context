package uk.co.idv.lockout.adapter.json.policy.recordattempt.always;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.lockout.entities.policy.recordattempt.AlwaysRecordAttemptPolicy;

public class AlwaysRecordAttemptPolicyDeserializer extends StdDeserializer<AlwaysRecordAttemptPolicy> {

    public AlwaysRecordAttemptPolicyDeserializer() {
        super(AlwaysRecordAttemptPolicy.class);
    }

    @Override
    public AlwaysRecordAttemptPolicy deserialize(JsonParser parser, DeserializationContext context) {
        return new AlwaysRecordAttemptPolicy();
    }

}
