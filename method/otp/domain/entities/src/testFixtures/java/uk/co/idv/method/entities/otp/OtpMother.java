package uk.co.idv.method.entities.otp;


import uk.co.idv.method.entities.otp.delivery.DeliveryMethod;
import uk.co.idv.method.entities.otp.delivery.DeliveryMethods;
import uk.co.idv.method.entities.otp.delivery.DeliveryMethodsMother;

public interface OtpMother {

    static Otp build() {
        return builder().build();
    }

    static Otp withEmptyDeliveryMethods() {
        return withDeliveryMethods(DeliveryMethodsMother.empty());
    }

    static Otp withDeliveryMethod(DeliveryMethod deliveryMethod) {
        return OtpMother.withDeliveryMethods(DeliveryMethodsMother.with(deliveryMethod));
    }

    static Otp withDeliveryMethods(DeliveryMethods deliveryMethods) {
        return builder().deliveryMethods(deliveryMethods).build();
    }

    static Otp.OtpBuilder builder() {
        return Otp.builder()
                .config(OtpConfigMother.build())
                .deliveryMethods(DeliveryMethodsMother.oneOfEach());
    }

}
