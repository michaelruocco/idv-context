package uk.co.idv.context.entities.policy.method.otp.delivery.phone.sms;

import uk.co.idv.context.entities.policy.method.otp.delivery.phone.OtpPhoneNumberConfig;
import uk.co.idv.context.entities.policy.method.otp.delivery.phone.OtpPhoneNumberConfigMother;

import java.time.Duration;

public interface SmsDeliveryMethodConfigMother {

    static SmsDeliveryMethodConfig sms() {
        return withConfig(OtpPhoneNumberConfigMother.build());
    }

    static SmsDeliveryMethodConfig withAsyncSimSwap() {
        return withConfig(OtpPhoneNumberConfigMother.withAsyncSimSwap());
    }

    static SmsDeliveryMethodConfig withSimSwapTimeout(Duration timeout) {
        return withConfig(OtpPhoneNumberConfigMother.withSimSwapTimeout(timeout));
    }

    static SmsDeliveryMethodConfig withConfig(OtpPhoneNumberConfig config) {
        return new SmsDeliveryMethodConfig(config);
    }

}
