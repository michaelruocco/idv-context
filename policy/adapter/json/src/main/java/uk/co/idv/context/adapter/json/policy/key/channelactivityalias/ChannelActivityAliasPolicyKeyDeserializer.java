package uk.co.idv.context.adapter.json.policy.key.channelactivityalias;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.context.entities.policy.key.ChannelActivityAliasPolicyKey;
import uk.co.mruoc.json.jackson.JsonNodeConverter;
import uk.co.mruoc.json.jackson.JsonParserConverter;

import java.util.UUID;

public class ChannelActivityAliasPolicyKeyDeserializer extends StdDeserializer<ChannelActivityAliasPolicyKey> {

    public ChannelActivityAliasPolicyKeyDeserializer() {
        super(ChannelActivityAliasPolicyKey.class);
    }

    @Override
    public ChannelActivityAliasPolicyKey deserialize(JsonParser parser, DeserializationContext context) {
        JsonNode node = JsonParserConverter.toNode(parser);
        return ChannelActivityAliasPolicyKey.builder()
                .id(UUID.fromString(node.get("id").asText()))
                .priority(node.get("priority").asInt())
                .channelId(node.get("channelId").asText())
                .activityNames(JsonNodeConverter.toStringCollection(node.get("activityNames"), parser))
                .aliasTypes(JsonNodeConverter.toStringCollection(node.get("aliasTypes"), parser))
                .build();
    }

}
