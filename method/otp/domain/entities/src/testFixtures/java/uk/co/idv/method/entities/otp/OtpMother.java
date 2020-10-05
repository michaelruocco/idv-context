package uk.co.idv.context.entities.context.method.otp;

import uk.co.idv.context.entities.context.method.otp.delivery.DeliveryMethod;
import uk.co.idv.context.entities.context.method.otp.delivery.DeliveryMethods;
import uk.co.idv.context.entities.context.method.otp.delivery.DeliveryMethodsMother;
import uk.co.idv.context.entities.policy.method.otp.OtpConfigMother;

public interface OtpMother {

    static Otp build() {
        return builder().build();
    }

    static Otp withDeliveryMethod(DeliveryMethod deliveryMethod) {
        return OtpMother.withDeliveryMethods(DeliveryMethodsMother.with(deliveryMethod));
    }

    static Otp withDeliveryMethods(DeliveryMethods deliveryMethods) {
        return builder().deliveryMethods(deliveryMethods).build();
    }

    static Otp.OtpBuilder builder() {
        return Otp.builder()
                .name("one-time-passcode")
                .otpConfig(OtpConfigMother.build())
                .deliveryMethods(DeliveryMethodsMother.oneOfEach());
    }

}
