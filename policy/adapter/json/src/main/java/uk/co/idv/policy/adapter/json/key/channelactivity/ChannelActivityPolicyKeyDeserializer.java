package uk.co.idv.policy.adapter.json.key.channelactivity;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.policy.entities.policy.key.ChannelActivityPolicyKey;
import uk.co.mruoc.json.jackson.JsonParserConverter;

import static uk.co.idv.policy.adapter.json.key.CommonPolicyKeyFieldDeserializer.extractActivityNames;
import static uk.co.idv.policy.adapter.json.key.CommonPolicyKeyFieldDeserializer.extractChannelId;
import static uk.co.idv.policy.adapter.json.key.CommonPolicyKeyFieldDeserializer.extractId;
import static uk.co.idv.policy.adapter.json.key.CommonPolicyKeyFieldDeserializer.extractPriority;

public class ChannelActivityPolicyKeyDeserializer extends StdDeserializer<ChannelActivityPolicyKey> {

    public ChannelActivityPolicyKeyDeserializer() {
        super(ChannelActivityPolicyKey.class);
    }

    @Override
    public ChannelActivityPolicyKey deserialize(JsonParser parser, DeserializationContext context) {
        JsonNode node = JsonParserConverter.toNode(parser);
        return ChannelActivityPolicyKey.builder()
                .id(extractId(node))
                .priority(extractPriority(node))
                .channelId(extractChannelId(node))
                .activityNames(extractActivityNames(node, parser))
                .build();
    }

}
