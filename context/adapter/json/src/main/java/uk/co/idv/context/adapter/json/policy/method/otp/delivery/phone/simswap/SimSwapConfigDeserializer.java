package uk.co.idv.context.adapter.json.policy.method.otp.delivery.phone.simswap;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.context.entities.policy.method.otp.delivery.phone.simswap.AcceptableSimSwapStatuses;
import uk.co.idv.context.entities.policy.method.otp.delivery.phone.simswap.SimSwapConfig;
import uk.co.mruoc.json.jackson.JsonNodeConverter;
import uk.co.mruoc.json.jackson.JsonParserConverter;

import java.time.Duration;
import java.util.Optional;

public class SimSwapConfigDeserializer extends StdDeserializer<SimSwapConfig> {

    public SimSwapConfigDeserializer() {
        super(SimSwapConfig.class);
    }

    @Override
    public SimSwapConfig deserialize(JsonParser parser, DeserializationContext context) {
        JsonNode node = JsonParserConverter.toNode(parser);
        return SimSwapConfig.builder()
                .acceptableStatuses(JsonNodeConverter.toObject(node.get("acceptableStatuses"), parser, AcceptableSimSwapStatuses.class))
                .timeout(JsonNodeConverter.toObject(node.get("timeout"), parser, Duration.class))
                .minDaysSinceSwap(toMinDaysSinceSwapIfPresent(node))
                .build();
    }

    private static Long toMinDaysSinceSwapIfPresent(JsonNode node) {
        return Optional.ofNullable(node.get("minDaysSinceSwap"))
                .map(JsonNode::asLong)
                .orElse(null);
    }

}
