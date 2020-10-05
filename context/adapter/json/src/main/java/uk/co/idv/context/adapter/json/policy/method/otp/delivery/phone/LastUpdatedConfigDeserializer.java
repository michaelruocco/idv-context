package uk.co.idv.context.adapter.json.policy.method.otp.delivery.phone;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.method.entities.otp.policy.delivery.LastUpdatedConfig;
import uk.co.mruoc.json.jackson.JsonParserConverter;

import java.util.Optional;

public class LastUpdatedConfigDeserializer extends StdDeserializer<LastUpdatedConfig> {

    public LastUpdatedConfigDeserializer() {
        super(LastUpdatedConfig.class);
    }

    @Override
    public LastUpdatedConfig deserialize(JsonParser parser, DeserializationContext context) {
        JsonNode node = JsonParserConverter.toNode(parser);
        return LastUpdatedConfig.builder()
                .allowUnknown(node.get("allowUnknown").asBoolean())
                .minDaysSinceUpdate(toMinDaysSinceUpdateIfPresent(node))
                .build();
    }

    private static Long toMinDaysSinceUpdateIfPresent(JsonNode node) {
        return Optional.ofNullable(node.get("minDaysSinceUpdate"))
                .map(JsonNode::asLong)
                .orElse(null);
    }

}
