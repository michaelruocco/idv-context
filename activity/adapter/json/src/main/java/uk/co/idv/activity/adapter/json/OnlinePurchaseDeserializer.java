package uk.co.idv.activity.adapter.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.activity.entities.OnlinePurchase;
import uk.co.mruoc.json.jackson.JsonNodeConverter;
import uk.co.mruoc.json.jackson.JsonParserConverter;

import javax.money.MonetaryAmount;
import java.time.Instant;

class OnlinePurchaseDeserializer extends StdDeserializer<OnlinePurchase> {

    protected OnlinePurchaseDeserializer() {
        super(OnlinePurchase.class);
    }

    @Override
    public OnlinePurchase deserialize(JsonParser parser, DeserializationContext context) {
        JsonNode node = JsonParserConverter.toNode(parser);
        return OnlinePurchase.builder()
                .merchantName(node.get("merchantName").asText())
                .reference(node.get("reference").asText())
                .cost(JsonNodeConverter.toObject(node.get("cost"), parser, MonetaryAmount.class))
                .last4DigitsOfCardNumber(node.get("last4DigitsOfCardNumber").asText())
                .timestamp(JsonNodeConverter.toObject(node.get("timestamp"), parser, Instant.class))
                .build();
    }

}
