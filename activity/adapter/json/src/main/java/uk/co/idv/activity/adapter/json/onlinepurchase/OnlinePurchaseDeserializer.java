package uk.co.idv.activity.adapter.json.onlinepurchase;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.activity.entities.onlinepurchase.OnlinePurchase;
import uk.co.idv.activity.entities.onlinepurchase.PurchaseCard;
import uk.co.mruoc.json.jackson.JsonNodeConverter;
import uk.co.mruoc.json.jackson.JsonParserConverter;

import javax.money.MonetaryAmount;
import java.time.Instant;

public class OnlinePurchaseDeserializer extends StdDeserializer<OnlinePurchase> {

    public OnlinePurchaseDeserializer() {
        super(OnlinePurchase.class);
    }

    @Override
    public OnlinePurchase deserialize(JsonParser parser, DeserializationContext context) {
        JsonNode node = JsonParserConverter.toNode(parser);
        return OnlinePurchase.builder()
                .merchantName(node.get("merchantName").asText())
                .reference(node.get("reference").asText())
                .cost(JsonNodeConverter.toObject(node.get("cost"), parser, MonetaryAmount.class))
                .card(JsonNodeConverter.toObject(node.get("card"), parser, PurchaseCard.class))
                .timestamp(JsonNodeConverter.toObject(node.get("timestamp"), parser, Instant.class))
                .build();
    }

}
