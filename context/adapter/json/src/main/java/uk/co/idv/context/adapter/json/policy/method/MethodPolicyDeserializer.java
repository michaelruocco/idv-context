package uk.co.idv.context.adapter.json.policy.method;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.context.entities.policy.method.MethodPolicy;
import uk.co.idv.context.entities.policy.method.otp.OtpPolicy;
import uk.co.mruoc.json.jackson.JsonNodeConverter;
import uk.co.mruoc.json.jackson.JsonParserConverter;

import java.util.Map;
import java.util.Optional;

public class MethodPolicyDeserializer extends StdDeserializer<MethodPolicy> {

    private final Map<String, Class<? extends MethodPolicy>> mappings;

    protected MethodPolicyDeserializer() {
        this(buildMappings());
    }

    public MethodPolicyDeserializer(Map<String, Class<? extends MethodPolicy>> mappings) {
        super(MethodPolicy.class);
        this.mappings = mappings;
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

    private static Map<String, Class<? extends MethodPolicy>> buildMappings() {
        return Map.of(OtpPolicy.NAME, OtpPolicy.class);
    }

}
