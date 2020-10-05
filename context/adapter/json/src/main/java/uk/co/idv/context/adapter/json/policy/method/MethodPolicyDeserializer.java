package uk.co.idv.context.adapter.json.policy.method;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.method.adapter.json.MethodMapping;
import uk.co.idv.method.entities.policy.MethodPolicy;
import uk.co.mruoc.json.jackson.JsonNodeConverter;
import uk.co.mruoc.json.jackson.JsonParserConverter;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class MethodPolicyDeserializer extends StdDeserializer<MethodPolicy> {

    private final Map<String, Class<? extends MethodPolicy>> mappings;

    public MethodPolicyDeserializer(Collection<MethodMapping> mappings) {
        super(MethodPolicy.class);
        this.mappings = toMap(mappings);
    }

    @Override
    public MethodPolicy deserialize(JsonParser parser, DeserializationContext context) {
        JsonNode node = JsonParserConverter.toNode(parser);
        String name = extractName(node);
        return JsonNodeConverter.toObject(node, parser, toMappingType(name));
    }

    private static String extractName(JsonNode node) {
        return node.get("name").asText();
    }

    private Class<? extends MethodPolicy> toMappingType(String name) {
        return Optional.ofNullable(mappings.get(name))
                .orElseThrow(() -> new InvalidMethodPolicyNameException(name));
    }

    private static Map<String, Class<? extends MethodPolicy>> toMap(Collection<MethodMapping> mappings) {
        return mappings.stream().collect(Collectors.toMap(
                MethodMapping::getName,
                MethodMapping::getPolicyType
        ));
    }

}
