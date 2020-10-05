package uk.co.idv.method.entities.otp.delivery.email;

import uk.co.idv.method.entities.eligibility.EligibilityMother;
import uk.co.idv.method.entities.otp.delivery.DeliveryMethod;

import java.util.UUID;

public interface EmailDeliveryMethodMother {

    static DeliveryMethod email() {
        return builder().build();
    }

    static DeliveryMethod.DeliveryMethodBuilder builder() {
        return DeliveryMethod.builder()
                .id(UUID.fromString("51c560bd-4b47-4209-b70f-74f82720d960"))
                .type("email")
                .value("joe.bloggs@hotmail.com")
                .eligibility(EligibilityMother.eligible());
    }

}
