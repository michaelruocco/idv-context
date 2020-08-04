package uk.co.idv.context.adapter.json.lockout.policy.state.soft;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.context.entities.lockout.policy.soft.SoftLockInterval;
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
                .duration(Duration.ofMillis(node.get("duration").asLong()))
                .build();
    }

}
