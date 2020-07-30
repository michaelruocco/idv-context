package uk.co.idv.context.adapter.json.policy.key;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.context.entities.policy.PolicyKey;
import uk.co.idv.context.entities.policy.key.ChannelActivityAliasPolicyKey;
import uk.co.idv.context.entities.policy.key.ChannelActivityPolicyKey;
import uk.co.idv.context.entities.policy.key.ChannelPolicyKey;
import uk.co.mruoc.json.jackson.JsonNodeConverter;
import uk.co.mruoc.json.jackson.JsonParserConverter;

import java.util.Map;
import java.util.Optional;

public class PolicyKeyDeserializer extends StdDeserializer<PolicyKey> {

    private final Map<String, Class<? extends PolicyKey>> mappings;

    protected PolicyKeyDeserializer() {
        this(buildMappings());
    }

    public PolicyKeyDeserializer(Map<String, Class<? extends PolicyKey>> mappings) {
        super(PolicyKey.class);
        this.mappings = mappings;
    }

    @Override
    public PolicyKey deserialize(JsonParser parser, DeserializationContext context) {
        JsonNode node = JsonParserConverter.toNode(parser);
        String type = extractType(node);
        return JsonNodeConverter.toObject(node, parser, toMappingType(type));
    }

    private static String extractType(JsonNode node) {
        return node.get("type").asText();
    }

    private Class<? extends PolicyKey> toMappingType(String name) {
        return Optional.ofNullable(mappings.get(name))
                .orElseThrow(() -> new InvalidPolicyKeyTypeException(name));
    }

    private static Map<String, Class<? extends PolicyKey>> buildMappings() {
        return Map.of(
                ChannelPolicyKey.TYPE, ChannelPolicyKey.class,
                ChannelActivityPolicyKey.TYPE, ChannelActivityPolicyKey.class,
                ChannelActivityAliasPolicyKey.TYPE, ChannelActivityAliasPolicyKey.class
        );
    }

}
