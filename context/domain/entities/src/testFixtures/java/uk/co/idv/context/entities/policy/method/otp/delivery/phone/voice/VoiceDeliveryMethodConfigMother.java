package uk.co.idv.context.entities.policy.method.otp.delivery.phone.voice;

import uk.co.idv.context.entities.policy.method.otp.delivery.phone.OtpPhoneNumberConfig;
import uk.co.idv.context.entities.policy.method.otp.delivery.phone.OtpPhoneNumberConfigMother;

import java.time.Duration;

public interface VoiceDeliveryMethodConfigMother {

    static VoiceDeliveryMethodConfig build() {
        return withConfig(OtpPhoneNumberConfigMother.build());
    }

    static VoiceDeliveryMethodConfig withSimSwapTimeout(Duration timeout) {
        return withConfig(OtpPhoneNumberConfigMother.withSimSwapTimeout(timeout));
    }

    static VoiceDeliveryMethodConfig withConfig(OtpPhoneNumberConfig config) {
        return new VoiceDeliveryMethodConfig(config);
    }

}
