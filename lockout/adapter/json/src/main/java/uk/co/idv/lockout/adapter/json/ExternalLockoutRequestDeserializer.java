package uk.co.idv.lockout.adapter.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.identity.entities.alias.Aliases;
import uk.co.idv.lockout.entities.DefaultExternalLockoutRequest;
import uk.co.idv.lockout.entities.ExternalLockoutRequest;
import uk.co.mruoc.json.jackson.JsonNodeConverter;
import uk.co.mruoc.json.jackson.JsonParserConverter;

public class ExternalLockoutRequestDeserializer extends StdDeserializer<ExternalLockoutRequest> {

    public ExternalLockoutRequestDeserializer() {
        super(ExternalLockoutRequest.class);
    }

    @Override
    public ExternalLockoutRequest deserialize(JsonParser parser, DeserializationContext context) {
        JsonNode node = JsonParserConverter.toNode(parser);
        return DefaultExternalLockoutRequest.builder()
                .channelId(node.get("channelId").asText())
                .activityName(node.get("activityName").asText())
                .aliases(JsonNodeConverter.toObject(node.get("aliases"), parser, Aliases.class))
                .build();
    }

}
