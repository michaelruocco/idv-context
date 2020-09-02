package uk.co.idv.policy.adapter.json.key;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import uk.co.mruoc.json.jackson.JsonNodeConverter;

import java.util.Collection;
import java.util.UUID;

public interface CommonPolicyKeyFieldDeserializer {

    static UUID extractId(JsonNode node) {
        return UUID.fromString(node.get("id").asText());
    }

    static int extractPriority(JsonNode node) {
        return node.get("priority").asInt();
    }

    static String extractChannelId(JsonNode node) {
        return node.get("channelId").asText();
    }

    static Collection<String> extractActivityNames(JsonNode node, JsonParser parser) {
        return JsonNodeConverter.toStringCollection(node.get("activityNames"), parser);
    }

    static Collection<String> extractAliasTypes(JsonNode node, JsonParser parser) {
        return JsonNodeConverter.toStringCollection(node.get("aliasTypes"), parser);
    }

}
