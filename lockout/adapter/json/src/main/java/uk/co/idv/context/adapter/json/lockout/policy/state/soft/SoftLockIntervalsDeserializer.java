package uk.co.idv.context.adapter.json.lockout.policy.state.soft;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.context.entities.lockout.policy.soft.SoftLockInterval;
import uk.co.idv.context.entities.lockout.policy.soft.SoftLockIntervals;
import uk.co.mruoc.json.jackson.JsonParserConverter;

import java.util.Collection;

import static uk.co.mruoc.json.jackson.JsonNodeConverter.toCollection;

public class SoftLockIntervalsDeserializer extends StdDeserializer<SoftLockIntervals> {

    private static final TypeReference<Collection<SoftLockInterval>> INTERVAL_COLLECTION = new TypeReference<>() {
        // intentionally blank
    };

    public SoftLockIntervalsDeserializer() {
        super(SoftLockIntervals.class);
    }

    @Override
    public SoftLockIntervals deserialize(JsonParser parser, DeserializationContext context) {
        JsonNode node = JsonParserConverter.toNode(parser);
        return new SoftLockIntervals(toCollection(node, parser, INTERVAL_COLLECTION));
    }

}
