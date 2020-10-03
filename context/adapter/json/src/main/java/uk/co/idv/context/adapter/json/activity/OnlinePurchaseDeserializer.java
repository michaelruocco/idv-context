package uk.co.idv.context.adapter.json.activity;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.context.entities.activity.OnlinePurchase;
import uk.co.idv.identity.entities.alias.CardNumber;
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
                .cardNumber(JsonNodeConverter.toObject(node.get("cardNumber"), parser, CardNumber.class))
                .timestamp(JsonNodeConverter.toObject(node.get("timestamp"), parser, Instant.class))
                .build();
    }

}
