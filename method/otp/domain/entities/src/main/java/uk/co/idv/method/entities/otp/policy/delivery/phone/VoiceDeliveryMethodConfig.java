package uk.co.idv.method.entities.otp.policy.delivery.phone;

public class VoiceDeliveryMethodConfig extends PhoneDeliveryMethodConfig {

    public static final String TYPE = "voice";

    public VoiceDeliveryMethodConfig(OtpPhoneNumberConfig phoneNumberConfig) {
        super(TYPE, phoneNumberConfig);
    }

}
