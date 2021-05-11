package uk.co.idv.method.adapter.json.push.policy;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.method.entities.push.PushNotificationConfig;
import uk.co.mruoc.json.jackson.JsonNodeConverter;
import uk.co.mruoc.json.jackson.JsonParserConverter;

import java.time.Duration;

public class PushNotificationConfigDeserializer extends StdDeserializer<PushNotificationConfig> {

    protected PushNotificationConfigDeserializer() {
        super(PushNotificationConfig.class);
    }

    @Override
    public PushNotificationConfig deserialize(JsonParser parser, DeserializationContext context) {
        JsonNode node = JsonParserConverter.toNode(parser);
        return PushNotificationConfig.builder()
                .maxNumberOfAttempts(node.get("maxNumberOfAttempts").asInt())
                .duration(JsonNodeConverter.toObject(node.get("duration"), parser, Duration.class))
                .build();
    }

}
