package uk.co.idv.context.entities.policy.method.otp.delivery.phone;

public class VoiceDeliveryMethodConfig extends PhoneDeliveryMethodConfig {

    public static final String TYPE = "voice";

    public VoiceDeliveryMethodConfig(OtpPhoneNumberConfig phoneNumberConfig) {
        super(TYPE, phoneNumberConfig);
    }

}
