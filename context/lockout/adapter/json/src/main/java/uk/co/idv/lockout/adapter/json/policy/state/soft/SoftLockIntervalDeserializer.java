package uk.co.idv.lockout.adapter.json.policy.state.soft;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.lockout.entities.policy.soft.SoftLockInterval;
import uk.co.mruoc.json.jackson.JsonNodeConverter;
import uk.co.mruoc.json.jackson.JsonParserConverter;

import java.time.Duration;

public class SoftLockIntervalDeserializer extends StdDeserializer<SoftLockInterval> {

    public SoftLockIntervalDeserializer() {
        super(SoftLockInterval.class);
    }

    @Override
    public SoftLockInterval deserialize(JsonParser parser, DeserializationContext context) {
        JsonNode node = JsonParserConverter.toNode(parser);
        return SoftLockInterval.builder()
                .numberOfAttempts(node.get("numberOfAttempts").asInt())
                .duration(JsonNodeConverter.toObject(node.get("duration"), parser, Duration.class))
                .build();
    }

}
