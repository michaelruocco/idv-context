package uk.co.idv.lockout.adapter.json.policy;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.lockout.entities.policy.AttemptsFilter;
import uk.co.idv.lockout.entities.policy.LockoutPolicy;
import uk.co.idv.lockout.entities.policy.LockoutStateCalculator;
import uk.co.idv.lockout.entities.policy.RecordAttemptPolicy;
import uk.co.idv.policy.entities.policy.key.PolicyKey;
import uk.co.mruoc.json.jackson.JsonNodeConverter;
import uk.co.mruoc.json.jackson.JsonParserConverter;

public class LockoutPolicyDeserializer extends StdDeserializer<LockoutPolicy> {

    public LockoutPolicyDeserializer() {
        super(LockoutPolicy.class);
    }

    @Override
    public LockoutPolicy deserialize(JsonParser parser, DeserializationContext context) {
        JsonNode node = JsonParserConverter.toNode(parser);
        PolicyKey key = JsonNodeConverter.toObject(node.get("key"), parser, PolicyKey.class);
        return LockoutPolicy.builder()
                .key(key)
                .attemptsFilter(new AttemptsFilter(key))
                .recordAttemptPolicy(JsonNodeConverter.toObject(node.get("recordAttemptPolicy"), parser, RecordAttemptPolicy.class))
                .stateCalculator(JsonNodeConverter.toObject(node.get("stateCalculator"), parser, LockoutStateCalculator.class))
                .build();
    }

}
