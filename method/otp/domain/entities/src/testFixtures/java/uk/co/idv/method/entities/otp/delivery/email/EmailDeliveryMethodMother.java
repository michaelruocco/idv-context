package uk.co.idv.context.entities.context.method.otp.delivery;

import uk.co.idv.context.entities.context.method.otp.delivery.DeliveryMethod.DeliveryMethodBuilder;
import uk.co.idv.method.entities.eligibility.EligibilityMother;

import java.util.UUID;

public interface EmailDeliveryMethodMother {

    static DeliveryMethod email() {
        return builder().build();
    }

    static DeliveryMethodBuilder builder() {
        return DeliveryMethod.builder()
                .id(UUID.fromString("51c560bd-4b47-4209-b70f-74f82720d960"))
                .type("email")
                .value("joe.bloggs@hotmail.com")
                .eligibility(EligibilityMother.eligible());
    }

}
