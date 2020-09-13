package uk.co.idv.context.entities.context.method.otp.delivery;

import uk.co.idv.context.entities.context.eligibility.Eligible;
import uk.co.idv.context.entities.context.method.otp.delivery.DeliveryMethod.DeliveryMethodBuilder;

import java.util.UUID;

public interface SmsDeliveryMethodMother {

    static DeliveryMethod sms() {
        return builder().build();
    }

    static DeliveryMethodBuilder builder() {
        return DeliveryMethod.builder()
                .id(UUID.fromString("cedbf56f-66d5-41e0-a669-01559b031c7c"))
                .type("sms")
                .value("+447809123456")
                .eligibility(new Eligible());
    }

}
