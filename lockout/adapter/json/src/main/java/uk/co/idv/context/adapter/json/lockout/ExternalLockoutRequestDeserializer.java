package uk.co.idv.context.adapter.json.lockout;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.context.entities.alias.Aliases;
import uk.co.idv.context.entities.lockout.DefaultExternalLockoutRequest;
import uk.co.idv.context.entities.lockout.ExternalLockoutRequest;
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
