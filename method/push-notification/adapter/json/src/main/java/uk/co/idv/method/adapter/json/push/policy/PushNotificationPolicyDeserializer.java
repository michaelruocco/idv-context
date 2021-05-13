package uk.co.idv.method.adapter.json.push.policy;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.method.entities.push.PushNotificationConfig;
import uk.co.idv.method.entities.push.policy.PushNotificationPolicy;
import uk.co.mruoc.json.jackson.JsonNodeConverter;
import uk.co.mruoc.json.jackson.JsonParserConverter;

public class PushNotificationPolicyDeserializer extends StdDeserializer<PushNotificationPolicy> {

    protected PushNotificationPolicyDeserializer() {
        super(PushNotificationPolicy.class);
    }

    @Override
    public PushNotificationPolicy deserialize(JsonParser parser, DeserializationContext context) {
        JsonNode node = JsonParserConverter.toNode(parser);
        return PushNotificationPolicy.builder()
                .config(JsonNodeConverter.toObject(node.get("config"), parser, PushNotificationConfig.class))
                .build();
    }

}
