package uk.co.idv.context.adapter.json.context.method;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.context.entities.context.method.Method;
import uk.co.idv.context.entities.context.method.otp.Otp;
import uk.co.idv.context.entities.policy.method.otp.OtpPolicy;
import uk.co.mruoc.json.jackson.JsonNodeConverter;
import uk.co.mruoc.json.jackson.JsonParserConverter;

import java.util.Map;
import java.util.Optional;

public class MethodDeserializer extends StdDeserializer<Method> {

    private final Map<String, Class<? extends Method>> mappings;

    protected MethodDeserializer() {
        this(buildMappings());
    }

    public MethodDeserializer(Map<String, Class<? extends Method>> mappings) {
        super(Method.class);
        this.mappings = mappings;
    }

    @Override
    public Method deserialize(JsonParser parser, DeserializationContext context) {
        JsonNode node = JsonParserConverter.toNode(parser);
        String name = extractName(node);
        return JsonNodeConverter.toObject(node, parser, toMappingType(name));
    }

    private static String extractName(JsonNode node) {
        return node.get("name").asText();
    }

    private Class<? extends Method> toMappingType(String name) {
        return Optional.ofNullable(mappings.get(name))
                .orElseThrow(() -> new InvalidMethodNameException(name));
    }

    private static Map<String, Class<? extends Method>> buildMappings() {
        return Map.of(
                OtpPolicy.NAME, Otp.class
        );
    }

}
