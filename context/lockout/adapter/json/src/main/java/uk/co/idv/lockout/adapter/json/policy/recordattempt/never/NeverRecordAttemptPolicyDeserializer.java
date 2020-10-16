package uk.co.idv.lockout.adapter.json.policy.recordattempt.never;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.lockout.entities.policy.recordattempt.NeverRecordAttemptPolicy;

public class NeverRecordAttemptPolicyDeserializer extends StdDeserializer<NeverRecordAttemptPolicy> {

    public NeverRecordAttemptPolicyDeserializer() {
        super(NeverRecordAttemptPolicy.class);
    }

    @Override
    public NeverRecordAttemptPolicy deserialize(JsonParser parser, DeserializationContext context) {
        return new NeverRecordAttemptPolicy();
    }

}
