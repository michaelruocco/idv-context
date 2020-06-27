package uk.co.idv.context.adapter.json.phonenumber;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.context.adapter.json.JsonNodeConverter;
import uk.co.idv.context.adapter.json.JsonParserConverter;
import uk.co.idv.context.entities.phonenumber.PhoneNumber;
import uk.co.idv.context.entities.phonenumber.PhoneNumbers;

public class PhoneNumbersDeserializer extends StdDeserializer<PhoneNumbers> {

    public PhoneNumbersDeserializer() {
        super(PhoneNumbers.class);
    }

    @Override
    public PhoneNumbers deserialize(final JsonParser parser,final DeserializationContext context) {
        final JsonNode node = JsonParserConverter.toNode(parser);
        final PhoneNumber[] numbers = JsonNodeConverter.toObject(node, parser, PhoneNumber[].class);
        return new PhoneNumbers(numbers);
    }

}
