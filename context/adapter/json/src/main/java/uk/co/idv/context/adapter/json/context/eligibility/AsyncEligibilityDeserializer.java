package uk.co.idv.context.adapter.json.context.eligibility;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.method.entities.eligibility.AsyncEligibility;
import uk.co.idv.method.entities.eligibility.DefaultAsyncEligibility;
import uk.co.mruoc.json.jackson.JsonParserConverter;

import java.util.Optional;

class AsyncEligibilityDeserializer extends StdDeserializer<AsyncEligibility> {

    protected AsyncEligibilityDeserializer() {
        super(AsyncEligibility.class);
    }

    @Override
    public AsyncEligibility deserialize(JsonParser parser, DeserializationContext context) {
        JsonNode node = JsonParserConverter.toNode(parser);
        return DefaultAsyncEligibility.builder()
                .eligible(node.get("eligible").asBoolean())
                .complete(node.get("complete").asBoolean())
                .reason(extractReason(node))
                .build();
    }

    private String extractReason(JsonNode node) {
        return Optional.ofNullable(node.get("reason"))
                .map(JsonNode::asText)
                .orElse(null);
    }

}
