package uk.co.idv.context.adapter.json.alias.creditcardnumber;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.context.adapter.json.JsonParserConverter;
import uk.co.idv.context.entities.alias.CreditCardNumber;

public class CreditCardNumberDeserializer extends StdDeserializer<CreditCardNumber> {

    public CreditCardNumberDeserializer() {
        super(CreditCardNumber.class);
    }

    @Override
    public CreditCardNumber deserialize(final JsonParser parser, final DeserializationContext context) {
        final JsonNode node = JsonParserConverter.toNode(parser);
        return new CreditCardNumber(node.get("value").asText());
    }

}
