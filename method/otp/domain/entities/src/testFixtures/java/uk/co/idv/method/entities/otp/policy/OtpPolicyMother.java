package uk.co.idv.method.entities.otp.policy;

import uk.co.idv.method.entities.otp.OtpConfigMother;
import uk.co.idv.method.entities.otp.policy.delivery.DeliveryMethodConfig;
import uk.co.idv.method.entities.otp.policy.delivery.DeliveryMethodConfigs;
import uk.co.idv.method.entities.otp.policy.delivery.DeliveryMethodConfigsMother;
import uk.co.idv.method.entities.otp.policy.delivery.phone.sms.SmsDeliveryMethodConfigMother;

public interface OtpPolicyMother {

    static OtpPolicy build() {
        return builder().build();
    }

    static OtpPolicy withSmsAsyncSimSwap() {
        return withDeliveryMethodConfigs(SmsDeliveryMethodConfigMother.withAsyncSimSwap());
    }

    static OtpPolicy withDeliveryMethodConfigs(DeliveryMethodConfig... configs) {
        return builder().deliveryMethodConfigs(DeliveryMethodConfigsMother.with(configs)).build();
    }

    static OtpPolicy withDeliveryMethodConfigs(DeliveryMethodConfigs configs) {
        return builder().deliveryMethodConfigs(configs).build();
    }

    static OtpPolicy.OtpPolicyBuilder builder() {
        return OtpPolicy.builder()
                .config(OtpConfigMother.build())
                .deliveryMethodConfigs(DeliveryMethodConfigsMother.oneOfEach());
    }

}
