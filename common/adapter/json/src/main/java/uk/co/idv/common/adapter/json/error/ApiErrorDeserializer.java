package uk.co.idv.common.adapter.json.error;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.mruoc.json.jackson.JsonNodeConverter;
import uk.co.mruoc.json.jackson.JsonParserConverter;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

public class ApiErrorDeserializer extends StdDeserializer<ApiError> {

    private static final TypeReference<Map<String, Object>> OBJECT_MAP_COLLECTION = new TypeReference<>() {
        // intentionally blank
    };

    public ApiErrorDeserializer() {
        super(ApiError.class);
    }

    @Override
    public ApiError deserialize(JsonParser parser, DeserializationContext context) {
        JsonNode node = JsonParserConverter.toNode(parser);
        return new DefaultApiError(
                node.get("status").asInt(),
                node.get("title").asText(),
                extractMessage(node),
                extractMeta(node, parser)
        );
    }

    private static String extractMessage(JsonNode node) {
        return Optional.ofNullable(node.get("message"))
                .map(JsonNode::asText)
                .orElse("");
    }

    private static Map<String, Object> extractMeta(JsonNode node, JsonParser parser) {
        return Optional.ofNullable(node.get("meta"))
                .map(metaNode -> JsonNodeConverter.toCollection(metaNode, parser, OBJECT_MAP_COLLECTION))
                .orElse(Collections.emptyMap());
    }

}
