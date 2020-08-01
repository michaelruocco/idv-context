package uk.co.idv.context.adapter.json.lockout.attempt;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.context.entities.alias.IdvId;
import uk.co.idv.context.entities.lockout.attempt.VerificationAttempt;
import uk.co.idv.context.entities.lockout.attempt.VerificationAttempts;
import uk.co.mruoc.json.jackson.JsonNodeConverter;
import uk.co.mruoc.json.jackson.JsonParserConverter;

import java.util.Collection;
import java.util.UUID;

public class VerificationAttemptsDeserializer extends StdDeserializer<VerificationAttempts> {

    private static final TypeReference<Collection<VerificationAttempt>> ATTEMPT_COLLECTION = new TypeReference<>() {
        // intentionally blank
    };

    public VerificationAttemptsDeserializer() {
        super(VerificationAttempts.class);
    }

    @Override
    public VerificationAttempts deserialize(JsonParser parser, DeserializationContext context) {
        JsonNode node = JsonParserConverter.toNode(parser);
        return VerificationAttempts.builder()
                .id(UUID.fromString(node.get("id").asText()))
                .idvId(new IdvId(node.get("idvId").asText()))
                .attempts(JsonNodeConverter.toCollection(node.get("attempts"), parser, ATTEMPT_COLLECTION))
                .build();
    }

}
