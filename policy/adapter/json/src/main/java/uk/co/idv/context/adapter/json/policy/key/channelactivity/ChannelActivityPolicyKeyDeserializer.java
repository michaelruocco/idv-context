package uk.co.idv.context.adapter.json.policy.key.channelactivity;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.context.entities.policy.key.ChannelActivityPolicyKey;
import uk.co.mruoc.json.jackson.JsonNodeConverter;
import uk.co.mruoc.json.jackson.JsonParserConverter;

import java.util.UUID;

public class ChannelActivityPolicyKeyDeserializer extends StdDeserializer<ChannelActivityPolicyKey> {

    public ChannelActivityPolicyKeyDeserializer() {
        super(ChannelActivityPolicyKey.class);
    }

    @Override
    public ChannelActivityPolicyKey deserialize(JsonParser parser, DeserializationContext context) {
        JsonNode node = JsonParserConverter.toNode(parser);
        return ChannelActivityPolicyKey.builder()
                .id(UUID.fromString(node.get("id").asText()))
                .priority(node.get("priority").asInt())
                .channelId(node.get("channelId").asText())
                .activityNames(JsonNodeConverter.toStringCollection(node.get("activityNames"), parser))
                .build();
    }

}
