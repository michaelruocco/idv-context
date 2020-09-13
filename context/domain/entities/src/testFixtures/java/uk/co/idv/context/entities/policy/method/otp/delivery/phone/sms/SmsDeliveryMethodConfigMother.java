package uk.co.idv.context.entities.policy.method.otp.delivery.phone.sms;

import uk.co.idv.context.entities.policy.method.otp.delivery.phone.OtpPhoneNumberConfig;
import uk.co.idv.context.entities.policy.method.otp.delivery.phone.OtpPhoneNumberConfigMother;

import java.time.Duration;

public interface SmsDeliveryMethodConfigMother {

    static SmsDeliveryMethodConfig build() {
        return withConfig(OtpPhoneNumberConfigMother.build());
    }

    static SmsDeliveryMethodConfig withSimSwapTimeout(Duration timeout) {
        return withConfig(OtpPhoneNumberConfigMother.withSimSwapTimeout(timeout));
    }

    static SmsDeliveryMethodConfig withConfig(OtpPhoneNumberConfig config) {
        return new SmsDeliveryMethodConfig(config);
    }

}
