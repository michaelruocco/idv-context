package uk.co.idv.context.adapter.json.phonenumber;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.context.entities.phonenumber.PhoneNumber;
import uk.co.idv.context.entities.phonenumber.PhoneNumbers;
import uk.co.mruoc.json.jackson.JsonNodeConverter;
import uk.co.mruoc.json.jackson.JsonParserConverter;

public class PhoneNumbersDeserializer extends StdDeserializer<PhoneNumbers> {

    public PhoneNumbersDeserializer() {
        super(PhoneNumbers.class);
    }

    @Override
    public PhoneNumbers deserialize(JsonParser parser, DeserializationContext context) {
        JsonNode node = JsonParserConverter.toNode(parser);
        PhoneNumber[] numbers = JsonNodeConverter.toObject(node, parser, PhoneNumber[].class);
        return new PhoneNumbers(numbers);
    }

}
