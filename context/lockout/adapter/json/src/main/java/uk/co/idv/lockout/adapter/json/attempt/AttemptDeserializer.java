package uk.co.idv.lockout.adapter.json.attempt;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.identity.entities.alias.Aliases;
import uk.co.idv.identity.entities.alias.IdvId;
import uk.co.idv.lockout.entities.attempt.Attempt;
import uk.co.mruoc.json.jackson.JsonNodeConverter;
import uk.co.mruoc.json.jackson.JsonParserConverter;

import java.time.Instant;
import java.util.UUID;

public class AttemptDeserializer extends StdDeserializer<Attempt> {

    public AttemptDeserializer() {
        super(Attempt.class);
    }

    @Override
    public Attempt deserialize(JsonParser parser, DeserializationContext context) {
        JsonNode node = JsonParserConverter.toNode(parser);
        return Attempt.builder()
                .channelId(node.get("channelId").asText())
                .activityName(node.get("activityName").asText())
                .aliases(JsonNodeConverter.toObject(node.get("aliases"), parser, Aliases.class))
                .idvId(JsonNodeConverter.toObject(node.get("idvId"), parser, IdvId.class))
                .contextId(UUID.fromString(node.get("contextId").asText()))
                .methodName(node.get("methodName").asText())
                .verificationId(UUID.fromString(node.get("verificationId").asText()))
                .timestamp(JsonNodeConverter.toObject(node.get("timestamp"), parser, Instant.class))
                .successful(node.get("successful").asBoolean())
                .build();
    }

}
