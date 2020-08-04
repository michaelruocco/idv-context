package uk.co.idv.context.adapter.json.lockout.policy.state.soft;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.context.entities.lockout.policy.soft.RecurringSoftLockoutStateCalculator;
import uk.co.idv.context.entities.lockout.policy.soft.SoftLockInterval;
import uk.co.mruoc.json.jackson.JsonNodeConverter;
import uk.co.mruoc.json.jackson.JsonParserConverter;

public class RecurringSoftLockoutStateCalculatorDeserializer extends StdDeserializer<RecurringSoftLockoutStateCalculator> {

    public RecurringSoftLockoutStateCalculatorDeserializer() {
        super(RecurringSoftLockoutStateCalculator.class);
    }

    @Override
    public RecurringSoftLockoutStateCalculator deserialize(JsonParser parser, DeserializationContext context) {
        JsonNode node = JsonParserConverter.toNode(parser);
        SoftLockInterval interval = JsonNodeConverter.toObject(node.get("interval"), parser, SoftLockInterval.class);
        return new RecurringSoftLockoutStateCalculator(interval);
    }

}
