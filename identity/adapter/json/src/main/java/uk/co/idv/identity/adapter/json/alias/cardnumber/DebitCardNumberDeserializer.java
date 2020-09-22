package uk.co.idv.identity.adapter.json.alias.cardnumber;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.identity.entities.alias.DebitCardNumber;
import uk.co.mruoc.json.jackson.JsonParserConverter;

public class DebitCardNumberDeserializer extends StdDeserializer<DebitCardNumber> {

    public DebitCardNumberDeserializer() {
        super(DebitCardNumber.class);
    }

    @Override
    public DebitCardNumber deserialize(JsonParser parser, DeserializationContext context) {
        JsonNode node = JsonParserConverter.toNode(parser);
        return new DebitCardNumber(node.get("value").asText());
    }

}
