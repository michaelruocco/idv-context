package uk.co.idv.method.adapter.json.verification;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.method.entities.verification.CompleteVerificationRequest;
import uk.co.mruoc.json.jackson.JsonParserConverter;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

class CompleteVerificationRequestDeserializer extends StdDeserializer<CompleteVerificationRequest> {

    protected CompleteVerificationRequestDeserializer() {
        super(CompleteVerificationRequest.class);
    }

    @Override
    public CompleteVerificationRequest deserialize(JsonParser parser, DeserializationContext context) {
        JsonNode node = JsonParserConverter.toNode(parser);
        return CompleteVerificationRequest.builder()
                .id(UUID.fromString(node.get("id").asText()))
                .contextId(UUID.fromString(node.get("contextId").asText()))
                .successful(node.get("successful").asBoolean())
                .timestamp(extractTimestamp(node).orElse(null))
                .build();
    }

    private static Optional<Instant> extractTimestamp(JsonNode node) {
        return Optional.ofNullable(node.get("timestamp"))
                .map(JsonNode::asText)
                .map(Instant::parse);
    }

}
