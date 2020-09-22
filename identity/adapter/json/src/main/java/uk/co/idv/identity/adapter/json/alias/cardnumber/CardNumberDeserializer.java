package uk.co.idv.identity.adapter.json.alias.cardnumber;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.identity.entities.alias.CardNumber;
import uk.co.idv.identity.entities.alias.CreditCardNumber;
import uk.co.idv.identity.entities.alias.DebitCardNumber;
import uk.co.mruoc.json.jackson.JsonNodeConverter;
import uk.co.mruoc.json.jackson.JsonParserConverter;

import java.util.Map;
import java.util.Optional;

public class CardNumberDeserializer extends StdDeserializer<CardNumber> {

    private final Map<String, Class<? extends CardNumber>> mappings;

    protected CardNumberDeserializer() {
        this(buildMappings());
    }

    public CardNumberDeserializer(Map<String, Class<? extends CardNumber>> mappings) {
        super(CardNumber.class);
        this.mappings = mappings;
    }

    @Override
    public CardNumber deserialize(JsonParser parser, DeserializationContext context) {
        JsonNode node = JsonParserConverter.toNode(parser);
        String type = extractType(node);
        return JsonNodeConverter.toObject(node, parser, toMappingType(type));
    }

    private static String extractType(JsonNode node) {
        return node.get("type").asText();
    }

    private Class<? extends CardNumber> toMappingType(String type) {
        return Optional.ofNullable(mappings.get(type))
                .orElseThrow(() -> new InvalidCardTypeException(type));
    }

    private static Map<String, Class<? extends CardNumber>> buildMappings() {
        return Map.of(
                CreditCardNumber.TYPE, CreditCardNumber.class,
                DebitCardNumber.TYPE, DebitCardNumber.class
        );
    }

}
