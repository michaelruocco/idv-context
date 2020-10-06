package uk.co.idv.method.adapter.json.otp.policy.delivery.phone;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import uk.co.idv.method.entities.otp.policy.delivery.phone.OtpPhoneNumberConfig;
import uk.co.mruoc.json.jackson.JsonNodeConverter;

public class PhoneJsonNodeConverter {

    private PhoneJsonNodeConverter() {
        // utility class
    }

    public static OtpPhoneNumberConfig toPhoneNumberConfig(JsonNode node, JsonParser parser) {
        return JsonNodeConverter.toObject(node, parser, OtpPhoneNumberConfig.class);
    }

}
