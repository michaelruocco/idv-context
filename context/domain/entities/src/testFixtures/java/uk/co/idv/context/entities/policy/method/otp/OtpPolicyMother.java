package uk.co.idv.context.entities.policy.method.otp;

import uk.co.idv.context.entities.policy.method.otp.delivery.DeliveryMethodConfigs;
import uk.co.idv.context.entities.policy.method.otp.delivery.DeliveryMethodConfigsMother;

public interface OtpPolicyMother {

    static OtpPolicy build() {
        return builder().build();
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
