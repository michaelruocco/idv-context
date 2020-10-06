package uk.co.idv.method.adapter.json.otp.policy.delivery.phone.voice;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.method.entities.otp.policy.delivery.phone.OtpPhoneNumberConfig;
import uk.co.idv.method.entities.otp.policy.delivery.phone.VoiceDeliveryMethodConfig;
import uk.co.mruoc.json.jackson.JsonParserConverter;

import static uk.co.idv.method.adapter.json.otp.policy.delivery.phone.PhoneJsonNodeConverter.toPhoneNumberConfig;

public class VoiceDeliveryMethodConfigDeserializer extends StdDeserializer<VoiceDeliveryMethodConfig> {

    public VoiceDeliveryMethodConfigDeserializer() {
        super(VoiceDeliveryMethodConfig.class);
    }

    @Override
    public VoiceDeliveryMethodConfig deserialize(JsonParser parser, DeserializationContext context) {
        JsonNode node = JsonParserConverter.toNode(parser);
        OtpPhoneNumberConfig phoneNumberConfig = toPhoneNumberConfig(node.get("phoneNumberConfig"), parser);
        return new VoiceDeliveryMethodConfig(phoneNumberConfig);
    }

}
