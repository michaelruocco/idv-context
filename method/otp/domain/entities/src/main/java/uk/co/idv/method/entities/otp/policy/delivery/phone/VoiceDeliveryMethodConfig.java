package uk.co.idv.context.entities.policy.method.otp.delivery.phone.voice;

import uk.co.idv.context.entities.policy.method.otp.delivery.phone.OtpPhoneNumberConfig;
import uk.co.idv.context.entities.policy.method.otp.delivery.phone.PhoneDeliveryMethodConfig;

public class VoiceDeliveryMethodConfig extends PhoneDeliveryMethodConfig {

    public static final String TYPE = "voice";

    public VoiceDeliveryMethodConfig(OtpPhoneNumberConfig phoneNumberConfig) {
        super(TYPE, phoneNumberConfig);
    }

}
