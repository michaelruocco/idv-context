package uk.co.idv.lockout.adapter.json.policy.state.hard;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.lockout.entities.policy.hard.HardLockoutStateCalculator;
import uk.co.idv.lockout.entities.policy.includeattempt.IncludeAttemptsPolicy;
import uk.co.mruoc.json.jackson.JsonNodeConverter;
import uk.co.mruoc.json.jackson.JsonParserConverter;

public class HardLockoutStateCalculatorDeserializer extends StdDeserializer<HardLockoutStateCalculator> {

    public HardLockoutStateCalculatorDeserializer() {
        super(HardLockoutStateCalculator.class);
    }

    @Override
    public HardLockoutStateCalculator deserialize(JsonParser parser, DeserializationContext context) {
        JsonNode node = JsonParserConverter.toNode(parser);
        return HardLockoutStateCalculator.builder()
                .maxNumberOfAttempts(node.get("maxNumberOfAttempts").asInt())
                .includeAttemptsPolicy(JsonNodeConverter.toObject(node.get("includeAttemptsPolicy"), parser, IncludeAttemptsPolicy.class))
                .build();
    }

}
