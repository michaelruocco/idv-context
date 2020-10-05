package uk.co.idv.method.entities.otp.policy.delivery.phone.voice;

import uk.co.idv.method.entities.otp.policy.delivery.phone.OtpPhoneNumberConfig;
import uk.co.idv.method.entities.otp.policy.delivery.phone.OtpPhoneNumberConfigMother;
import uk.co.idv.method.entities.otp.policy.delivery.phone.VoiceDeliveryMethodConfig;

import java.time.Duration;

public interface VoiceDeliveryMethodConfigMother {

    static VoiceDeliveryMethodConfig voice() {
        return withConfig(OtpPhoneNumberConfigMother.build());
    }

    static VoiceDeliveryMethodConfig withSimSwapTimeout(Duration timeout) {
        return withConfig(OtpPhoneNumberConfigMother.withSimSwapTimeout(timeout));
    }

    static VoiceDeliveryMethodConfig withConfig(OtpPhoneNumberConfig config) {
        return new VoiceDeliveryMethodConfig(config);
    }

}
