package uk.co.idv.identity.adapter.json.phonenumber;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.identity.entities.phonenumber.PhoneNumber;
import uk.co.mruoc.json.jackson.JsonParserConverter;

import java.time.Instant;
import java.util.Optional;

public class PhoneNumberDeserializer extends StdDeserializer<PhoneNumber> {

    protected PhoneNumberDeserializer() {
        super(PhoneNumber.class);
    }

    @Override
    public PhoneNumber deserialize(JsonParser parser, DeserializationContext context) {
        JsonNode node = JsonParserConverter.toNode(parser);
        return PhoneNumber.builder()
                .value(extractValue(node))
                .lastUpdated(extractLastUpdatedIfPresent(node))
                .build();
    }

    private static String extractValue(JsonNode node) {
        return node.get("value").asText();
    }

    private static Instant extractLastUpdatedIfPresent(JsonNode node) {
        return Optional.ofNullable(node.get("lastUpdated"))
                .map(JsonNode::asText)
                .map(Instant::parse)
                .orElse(null);
    }

}
