package uk.co.idv.context.adapter.json.policy.key;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import uk.co.mruoc.json.jackson.JsonNodeConverter;

import java.util.Collection;
import java.util.UUID;

public class CommonPolicyKeyFieldDeserializer {

    public static UUID extractId(JsonNode node) {
        return UUID.fromString(node.get("id").asText());
    }

    public static int extractPriority(JsonNode node) {
        return node.get("priority").asInt();
    }

    public static String extractChannelId(JsonNode node) {
        return node.get("channelId").asText();
    }

    public static Collection<String> extractActivityNames(JsonNode node, JsonParser parser) {
        return JsonNodeConverter.toStringCollection(node.get("activityNames"), parser);
    }

    public static Collection<String> extractAliasTypes(JsonNode node, JsonParser parser) {
        return JsonNodeConverter.toStringCollection(node.get("aliasTypes"), parser);
    }

}
