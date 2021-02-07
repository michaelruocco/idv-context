package uk.co.idv.identity.adapter.json.alias.cardnumber;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.identity.entities.alias.CardNumber;
import uk.co.mruoc.json.jackson.JsonParserConverter;

public class CardNumberDeserializer extends StdDeserializer<CardNumber> {

    public CardNumberDeserializer() {
        super(CardNumber.class);
    }

    @Override
    public CardNumber deserialize(JsonParser parser, DeserializationContext context) {
        JsonNode node = JsonParserConverter.toNode(parser);
        return toCardNumber(node);
    }

    private static CardNumber toCardNumber(JsonNode node) {
        String type = extractType(node);
        switch (type) {
            case CardNumber.CREDIT_TYPE:
                return CardNumber.credit(extractValue(node));
            case CardNumber.DEBIT_TYPE:
                return CardNumber.debit(extractValue(node));
            default:
                throw new InvalidCardTypeException(type);
        }
    }

    private static String extractType(JsonNode node) {
        return node.get("type").asText();
    }

    private static String extractValue(JsonNode node) {
        return node.get("value").asText();
    }

}
