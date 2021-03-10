package uk.co.idv.activity.adapter.json.onlinepurchase;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.activity.entities.onlinepurchase.PurchaseCard;
import uk.co.mruoc.json.jackson.JsonParserConverter;

import java.time.YearMonth;

public class PurchaseCardDeserializer extends StdDeserializer<PurchaseCard> {

    public PurchaseCardDeserializer() {
        super(PurchaseCard.class);
    }

    @Override
    public PurchaseCard deserialize(JsonParser parser, DeserializationContext context) {
        JsonNode node = JsonParserConverter.toNode(parser);
        return PurchaseCard.builder()
                .number(node.get("number").asText())
                .expiry(YearMonth.parse(node.get("expiry").asText()))
                .build();
    }

}
