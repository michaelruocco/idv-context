package uk.co.idv.identity.adapter.json.alias.creditcardnumber;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.identity.entities.alias.CreditCardNumber;
import uk.co.mruoc.json.jackson.JsonParserConverter;

public class CreditCardNumberDeserializer extends StdDeserializer<CreditCardNumber> {

    public CreditCardNumberDeserializer() {
        super(CreditCardNumber.class);
    }

    @Override
    public CreditCardNumber deserialize(JsonParser parser, DeserializationContext context) {
        JsonNode node = JsonParserConverter.toNode(parser);
        return new CreditCardNumber(node.get("value").asText());
    }

}
