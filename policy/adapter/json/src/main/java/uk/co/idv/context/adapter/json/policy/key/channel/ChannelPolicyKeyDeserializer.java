package uk.co.idv.context.adapter.json.policy.key.channel;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.context.entities.policy.key.ChannelPolicyKey;
import uk.co.mruoc.json.jackson.JsonParserConverter;

import java.util.UUID;

public class ChannelPolicyKeyDeserializer extends StdDeserializer<ChannelPolicyKey> {

    public ChannelPolicyKeyDeserializer() {
        super(ChannelPolicyKey.class);
    }

    @Override
    public ChannelPolicyKey deserialize(JsonParser parser, DeserializationContext context) {
        JsonNode node = JsonParserConverter.toNode(parser);
        return ChannelPolicyKey.builder()
                .id(UUID.fromString(node.get("id").asText()))
                .priority(node.get("priority").asInt())
                .channelId(node.get("channelId").asText())
                .build();
    }

}
