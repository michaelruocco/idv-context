package uk.co.idv.method.adapter.json.push;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.method.entities.push.PushNotification;
import uk.co.idv.method.entities.push.PushNotificationConfig;
import uk.co.mruoc.json.jackson.JsonNodeConverter;
import uk.co.mruoc.json.jackson.JsonParserConverter;

class PushNotificationDeserializer extends StdDeserializer<PushNotification> {

    protected PushNotificationDeserializer() {
        super(PushNotification.class);
    }

    @Override
    public PushNotification deserialize(JsonParser parser, DeserializationContext context) {
        JsonNode node = JsonParserConverter.toNode(parser);
        return PushNotification.builder()
                .config(JsonNodeConverter.toObject(node.get("config"), parser, PushNotificationConfig.class))
                .mobileDeviceTokens(JsonNodeConverter.toStringCollection(node.get("mobileDeviceTokens"), parser))
                .build();
    }

}
