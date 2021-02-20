package uk.co.idv.lockout.adapter.json.policy.includeattempt;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.lockout.entities.policy.includeattempt.IncludeAttemptsWithinDurationPolicy;
import uk.co.mruoc.json.jackson.JsonNodeConverter;
import uk.co.mruoc.json.jackson.JsonParserConverter;

import java.time.Clock;
import java.time.Duration;

public class IncludeAttemptsWithinDurationPolicyDeserializer extends StdDeserializer<IncludeAttemptsWithinDurationPolicy> {

    private final transient Clock clock;

    protected IncludeAttemptsWithinDurationPolicyDeserializer(Clock clock) {
        super(IncludeAttemptsWithinDurationPolicy.class);
        this.clock = clock;
    }

    @Override
    public IncludeAttemptsWithinDurationPolicy deserialize(JsonParser parser, DeserializationContext context) {
        JsonNode node = JsonParserConverter.toNode(parser);
        return IncludeAttemptsWithinDurationPolicy.builder()
                .clock(clock)
                .duration(JsonNodeConverter.toObject(node.get("duration"), parser, Duration.class))
                .build();
    }

}
