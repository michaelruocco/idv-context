package uk.co.idv.method.adapter.json.verification;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.method.entities.verification.CreateVerificationRequest;
import uk.co.mruoc.json.jackson.JsonParserConverter;

import java.util.UUID;

class CreateVerificationRequestDeserializer extends StdDeserializer<CreateVerificationRequest> {

    protected CreateVerificationRequestDeserializer() {
        super(CreateVerificationRequest.class);
    }

    @Override
    public CreateVerificationRequest deserialize(JsonParser parser, DeserializationContext context) {
        JsonNode node = JsonParserConverter.toNode(parser);
        return CreateVerificationRequest.builder()
                .contextId(UUID.fromString(node.get("contextId").asText()))
                .methodName(node.get("methodName").asText())
                .build();
    }

}
