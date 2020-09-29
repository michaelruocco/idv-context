package uk.co.idv.context.entities.context.method.otp.delivery;

import uk.co.idv.context.entities.context.eligibility.Eligibility;
import uk.co.idv.context.entities.context.eligibility.EligibilityMother;
import uk.co.idv.context.entities.context.method.otp.delivery.eligibility.AsyncSimSwapEligibilityMother;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface DeliveryMethodMother {

    static DeliveryMethod build() {
        return builder().build();
    }

    static DeliveryMethod eligible() {
        return withEligibility(EligibilityMother.eligible());
    }

    static DeliveryMethod ineligible() {
        return withEligibility(EligibilityMother.ineligible());
    }

    static DeliveryMethod withId(UUID id) {
        return builder().id(id).build();
    }


    static DeliveryMethod withEligibility(Eligibility eligibility) {
        return builder().eligibility(eligibility).build();
    }

    static DeliveryMethod withEligibilityFuture(CompletableFuture<Eligibility> future) {
        return withEligibility(AsyncSimSwapEligibilityMother.withFuture(future));
    }

    static DeliveryMethod.DeliveryMethodBuilder builder() {
        return DeliveryMethod.builder()
                .id(UUID.fromString("c9959188-969e-42f3-8178-42ef824c81d3"))
                .type("sms")
                .value("+4407808247743")
                .eligibility(EligibilityMother.eligible());
    }

}
