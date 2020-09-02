package uk.co.idv.context.adapter.json.phonenumber;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.identity.entities.phonenumber.PhoneNumber;
import uk.co.idv.identity.entities.phonenumber.PhoneNumbers;
import uk.co.mruoc.json.jackson.JsonNodeConverter;
import uk.co.mruoc.json.jackson.JsonParserConverter;

import java.util.Collection;

public class PhoneNumbersDeserializer extends StdDeserializer<PhoneNumbers> {

    private static final TypeReference<Collection<PhoneNumber>> PHONE_NUMBER_COLLECTION = new TypeReference<>() {
        // intentionally blank
    };

    public PhoneNumbersDeserializer() {
        super(PhoneNumbers.class);
    }

    @Override
    public PhoneNumbers deserialize(JsonParser parser, DeserializationContext context) {
        JsonNode node = JsonParserConverter.toNode(parser);
        return new PhoneNumbers(JsonNodeConverter.toCollection(node, parser, PHONE_NUMBER_COLLECTION));
    }

}
