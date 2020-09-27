package uk.co.idv.context.entities.context.method.otp.delivery;

import uk.co.idv.context.entities.context.eligibility.Eligibility;
import uk.co.idv.context.entities.context.eligibility.EligibilityMother;
import uk.co.idv.context.entities.context.method.otp.delivery.DeliveryMethod.DeliveryMethodBuilder;
import uk.co.idv.context.entities.context.method.otp.delivery.eligibility.AsyncSimSwapEligibilityMother;

import java.util.UUID;

public interface SmsDeliveryMethodMother {

    static DeliveryMethod sms() {
        return builder().build();
    }

    static DeliveryMethod smsWithAsyncSimSwapIneligible() {
        return withEligibility(AsyncSimSwapEligibilityMother.ineligible());
    }

    static DeliveryMethod withEligibility(Eligibility eligibility) {
        return builder().eligibility(eligibility).build();
    }

    static DeliveryMethodBuilder builder() {
        return DeliveryMethod.builder()
                .id(UUID.fromString("cedbf56f-66d5-41e0-a669-01559b031c7c"))
                .type("sms")
                .value("+447809123456")
                .eligibility(EligibilityMother.eligible());
    }

}
