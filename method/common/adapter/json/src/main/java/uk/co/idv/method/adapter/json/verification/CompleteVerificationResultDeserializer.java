package uk.co.idv.method.adapter.json.verification;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.method.entities.verification.CompleteVerificationResult;
import uk.co.idv.method.entities.verification.Verification;
import uk.co.mruoc.json.jackson.JsonNodeConverter;
import uk.co.mruoc.json.jackson.JsonParserConverter;

class CompleteVerificationResultDeserializer extends StdDeserializer<CompleteVerificationResult> {

    protected CompleteVerificationResultDeserializer() {
        super(CompleteVerificationResult.class);
    }

    @Override
    public CompleteVerificationResult deserialize(JsonParser parser, DeserializationContext context) {
        JsonNode node = JsonParserConverter.toNode(parser);
        return CompleteVerificationResult.builder()
                .contextComplete(node.get("contextComplete").asBoolean())
                .contextSuccessful(node.get("contextSuccessful").asBoolean())
                .verification(JsonNodeConverter.toObject(node.get("verification"), parser, Verification.class))
                .build();
    }

}
