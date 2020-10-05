package uk.co.idv.context.entities.policy.method.otp;

import uk.co.idv.context.entities.policy.method.otp.delivery.DeliveryMethodConfig;
import uk.co.idv.context.entities.policy.method.otp.delivery.DeliveryMethodConfigs;
import uk.co.idv.context.entities.policy.method.otp.delivery.DeliveryMethodConfigsMother;
import uk.co.idv.context.entities.policy.method.otp.delivery.phone.sms.SmsDeliveryMethodConfigMother;

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
                .methodConfig(OtpConfigMother.build())
                .deliveryMethodConfigs(DeliveryMethodConfigsMother.oneOfEach());
    }

}
