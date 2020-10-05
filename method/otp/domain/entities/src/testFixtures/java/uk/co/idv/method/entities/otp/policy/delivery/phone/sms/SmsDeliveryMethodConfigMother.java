package uk.co.idv.method.entities.otp.policy.delivery.phone.sms;

import uk.co.idv.method.entities.otp.policy.delivery.phone.OtpPhoneNumberConfig;
import uk.co.idv.method.entities.otp.policy.delivery.phone.OtpPhoneNumberConfigMother;
import uk.co.idv.method.entities.otp.policy.delivery.phone.SmsDeliveryMethodConfig;

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
