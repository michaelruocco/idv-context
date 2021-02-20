package uk.co.idv.lockout.adapter.json.policy.includeattempt;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.lockout.entities.policy.includeattempt.IncludeAllAttemptsPolicy;

public class IncludeAllAttemptsPolicyDeserializer extends StdDeserializer<IncludeAllAttemptsPolicy> {

    public IncludeAllAttemptsPolicyDeserializer() {
        super(IncludeAllAttemptsPolicy.class);
    }

    @Override
    public IncludeAllAttemptsPolicy deserialize(JsonParser parser, DeserializationContext context) {
        return new IncludeAllAttemptsPolicy();
    }

}
