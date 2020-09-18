package uk.co.idv.context.adapter.json.policy.method.otp.delivery.phone.sms;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.context.entities.policy.method.otp.delivery.phone.OtpPhoneNumberConfig;
import uk.co.idv.context.entities.policy.method.otp.delivery.phone.sms.SmsDeliveryMethodConfig;
import uk.co.mruoc.json.jackson.JsonParserConverter;

import static uk.co.idv.context.adapter.json.policy.method.otp.delivery.phone.PhoneJsonNodeConverter.toPhoneNumberConfig;

public class SmsDeliveryMethodConfigDeserializer extends StdDeserializer<SmsDeliveryMethodConfig> {

    public SmsDeliveryMethodConfigDeserializer() {
        super(SmsDeliveryMethodConfig.class);
    }

    @Override
    public SmsDeliveryMethodConfig deserialize(JsonParser parser, DeserializationContext context) {
        JsonNode node = JsonParserConverter.toNode(parser);
        OtpPhoneNumberConfig phoneNumberConfig = toPhoneNumberConfig(node.get("phoneNumberConfig"), parser);
        return new SmsDeliveryMethodConfig(phoneNumberConfig);
    }

}
