package uk.co.idv.context.adapter.json.verification;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.context.entities.activity.Activity;
import uk.co.idv.context.entities.verification.Verification;
import uk.co.idv.context.entities.context.method.Methods;
import uk.co.mruoc.json.jackson.JsonNodeConverter;
import uk.co.mruoc.json.jackson.JsonParserConverter;

import java.time.Instant;
import java.util.UUID;

class VerificationDeserializer extends StdDeserializer<Verification> {

    protected VerificationDeserializer() {
        super(Verification.class);
    }

    @Override
    public Verification deserialize(JsonParser parser, DeserializationContext context) {
        JsonNode node = JsonParserConverter.toNode(parser);
        return Verification.builder()
                .id(UUID.fromString(node.get("id").asText()))
                .contextId(UUID.fromString(node.get("contextId").asText()))
                .activity(JsonNodeConverter.toObject(node.get("activity"), parser, Activity.class))
                .methodName(node.get("methodName").asText())
                .methods(JsonNodeConverter.toObject(node.get("methods"), parser, Methods.class))
                .protectSensitiveData(node.get("protectSensitiveData").asBoolean())
                .created(Instant.parse(node.get("created").asText()))
                .expiry(Instant.parse(node.get("expiry").asText()))
                .build();
    }

}
