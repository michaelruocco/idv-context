package uk.co.idv.lockout.adapter.json.policy.includeattempt;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.lockout.entities.policy.includeattempt.IncludeAllAttemptsPolicy;
import uk.co.idv.lockout.entities.policy.includeattempt.IncludeAttemptsPolicy;
import uk.co.idv.lockout.entities.policy.includeattempt.IncludeAttemptsWithinDurationPolicy;
import uk.co.mruoc.json.jackson.JsonNodeConverter;
import uk.co.mruoc.json.jackson.JsonParserConverter;

import java.util.Map;
import java.util.Optional;

public class IncludeAttemptsPolicyDeserializer extends StdDeserializer<IncludeAttemptsPolicy> {

    private final Map<String, Class<? extends IncludeAttemptsPolicy>> mappings;

    protected IncludeAttemptsPolicyDeserializer() {
        this(buildMappings());
    }

    protected IncludeAttemptsPolicyDeserializer(Map<String, Class<? extends IncludeAttemptsPolicy>> mappings) {
        super(IncludeAttemptsPolicy.class);
        this.mappings = mappings;
    }

    @Override
    public IncludeAttemptsPolicy deserialize(JsonParser parser, DeserializationContext context) {
        JsonNode node = JsonParserConverter.toNode(parser);
        String type = extractType(node);
        return JsonNodeConverter.toObject(node, parser, toMappingType(type));
    }

    private static String extractType(JsonNode node) {
        return node.get("type").asText();
    }

    private Class<? extends IncludeAttemptsPolicy> toMappingType(String type) {
        return Optional.ofNullable(mappings.get(type))
                .orElseThrow(() -> new InvalidIncludeAttemptPolicyTypeException(type));
    }

    private static Map<String, Class<? extends IncludeAttemptsPolicy>> buildMappings() {
        return Map.of(
                IncludeAllAttemptsPolicy.TYPE, IncludeAllAttemptsPolicy.class,
                IncludeAttemptsWithinDurationPolicy.TYPE, IncludeAttemptsWithinDurationPolicy.class
        );
    }

}
