package uk.co.idv.lockout.adapter.json.policy.state.soft;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.lockout.entities.policy.includeattempt.IncludeAttemptsPolicy;
import uk.co.idv.lockout.entities.policy.soft.RecurringSoftLockoutStateCalculator;
import uk.co.idv.lockout.entities.policy.soft.SoftLockInterval;
import uk.co.mruoc.json.jackson.JsonNodeConverter;
import uk.co.mruoc.json.jackson.JsonParserConverter;

public class RecurringSoftLockoutStateCalculatorDeserializer extends StdDeserializer<RecurringSoftLockoutStateCalculator> {

    public RecurringSoftLockoutStateCalculatorDeserializer() {
        super(RecurringSoftLockoutStateCalculator.class);
    }

    @Override
    public RecurringSoftLockoutStateCalculator deserialize(JsonParser parser, DeserializationContext context) {
        JsonNode node = JsonParserConverter.toNode(parser);
        return RecurringSoftLockoutStateCalculator.builder()
                .interval(JsonNodeConverter.toObject(node.get("interval"), parser, SoftLockInterval.class))
                .includeAttemptsPolicy(JsonNodeConverter.toObject(node.get("includeAttemptsPolicy"), parser, IncludeAttemptsPolicy.class))
                .build();
    }

}
