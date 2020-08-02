package uk.co.idv.context.adapter.json.lockout.policy;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.context.entities.lockout.policy.AttemptsFilter;
import uk.co.idv.context.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.context.entities.lockout.policy.LockoutStateCalculator;
import uk.co.idv.context.entities.lockout.policy.RecordAttemptPolicy;
import uk.co.idv.context.entities.policy.PolicyKey;
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
