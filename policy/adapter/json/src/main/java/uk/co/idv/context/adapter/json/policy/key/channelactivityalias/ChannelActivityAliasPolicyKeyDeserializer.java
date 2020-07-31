package uk.co.idv.context.adapter.json.policy.key.channelactivityalias;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.context.entities.policy.key.ChannelActivityAliasPolicyKey;
import uk.co.mruoc.json.jackson.JsonParserConverter;

import static uk.co.idv.context.adapter.json.policy.key.CommonPolicyKeyFieldDeserializer.extractActivityNames;
import static uk.co.idv.context.adapter.json.policy.key.CommonPolicyKeyFieldDeserializer.extractAliasTypes;
import static uk.co.idv.context.adapter.json.policy.key.CommonPolicyKeyFieldDeserializer.extractChannelId;
import static uk.co.idv.context.adapter.json.policy.key.CommonPolicyKeyFieldDeserializer.extractId;
import static uk.co.idv.context.adapter.json.policy.key.CommonPolicyKeyFieldDeserializer.extractPriority;

public class ChannelActivityAliasPolicyKeyDeserializer extends StdDeserializer<ChannelActivityAliasPolicyKey> {

    public ChannelActivityAliasPolicyKeyDeserializer() {
        super(ChannelActivityAliasPolicyKey.class);
    }

    @Override
    public ChannelActivityAliasPolicyKey deserialize(JsonParser parser, DeserializationContext context) {
        JsonNode node = JsonParserConverter.toNode(parser);
        return ChannelActivityAliasPolicyKey.builder()
                .id(extractId(node))
                .priority(extractPriority(node))
                .channelId(extractChannelId(node))
                .activityNames(extractActivityNames(node, parser))
                .aliasTypes(extractAliasTypes(node, parser))
                .build();
    }

}
