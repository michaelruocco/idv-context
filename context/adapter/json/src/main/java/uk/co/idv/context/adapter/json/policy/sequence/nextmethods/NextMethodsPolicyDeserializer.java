package uk.co.idv.context.adapter.json.policy.sequence.nextmethods;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.context.entities.context.sequence.nextmethods.AnyOrderNextMethodsPolicy;
import uk.co.idv.context.entities.context.sequence.nextmethods.InOrderNextMethodsPolicy;
import uk.co.idv.context.entities.context.sequence.nextmethods.NextMethodsPolicy;
import uk.co.mruoc.json.jackson.JsonNodeConverter;
import uk.co.mruoc.json.jackson.JsonParserConverter;

import java.util.Map;
import java.util.Optional;

public class NextMethodsPolicyDeserializer extends StdDeserializer<NextMethodsPolicy> {

    private final Map<String, Class<? extends NextMethodsPolicy>> mappings;

    protected NextMethodsPolicyDeserializer() {
        this(buildMappings());
    }

    public NextMethodsPolicyDeserializer(Map<String, Class<? extends NextMethodsPolicy>> mappings) {
        super(NextMethodsPolicy.class);
        this.mappings = mappings;
    }

    @Override
    public NextMethodsPolicy deserialize(JsonParser parser, DeserializationContext context) {
        JsonNode node = JsonParserConverter.toNode(parser);
        String type = extractType(node);
        return JsonNodeConverter.toObject(node, parser, toMappingType(type));
    }

    private static String extractType(JsonNode node) {
        return node.get("type").asText();
    }

    private Class<? extends NextMethodsPolicy> toMappingType(String type) {
        return Optional.ofNullable(mappings.get(type))
                .orElseThrow(() -> new InvalidNextMethodPolicyTypeException(type));
    }

    private static Map<String, Class<? extends NextMethodsPolicy>> buildMappings() {
        return Map.of(
                AnyOrderNextMethodsPolicy.TYPE, AnyOrderNextMethodsPolicy.class,
                InOrderNextMethodsPolicy.TYPE, InOrderNextMethodsPolicy.class
        );
    }

}
