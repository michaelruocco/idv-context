package uk.co.idv.context.adapter.json.phonenumber;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.context.entities.phonenumber.MobilePhoneNumber;
import uk.co.idv.context.entities.phonenumber.OtherPhoneNumber;
import uk.co.idv.context.entities.phonenumber.PhoneNumber;
import uk.co.mruoc.json.jackson.JsonParserConverter;

public class PhoneNumberDeserializer extends StdDeserializer<PhoneNumber> {

    protected PhoneNumberDeserializer() {
        super(PhoneNumber.class);
    }

    @Override
    public PhoneNumber deserialize(JsonParser parser, DeserializationContext context) {
        JsonNode node = JsonParserConverter.toNode(parser);
        String type = node.get("type").asText();
        if (MobilePhoneNumber.isMobile(type)) {
            return toMobileNumber(node);
        }
        return toOtherNumber(node);
    }

    private static PhoneNumber toMobileNumber(JsonNode node) {
        return new MobilePhoneNumber(extractValue(node));
    }

    private static PhoneNumber toOtherNumber(JsonNode node) {
        return new OtherPhoneNumber(extractValue(node));
    }

    private static String extractValue(JsonNode node) {
        return node.get("value").asText();
    }

}
