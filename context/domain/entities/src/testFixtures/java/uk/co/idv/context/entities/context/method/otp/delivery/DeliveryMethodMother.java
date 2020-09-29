package uk.co.idv.context.entities.context.method.otp.delivery;

import uk.co.idv.context.entities.context.eligibility.Eligibility;
import uk.co.idv.context.entities.context.eligibility.EligibilityMother;
import uk.co.idv.context.entities.context.method.otp.delivery.eligibility.AsyncSimSwapEligibilityMother;

import java.time.Instant;
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

    static DeliveryMethod withLastUpdated(Instant lastUpdated) {
        return builder().lastUpdated(lastUpdated).build();
    }

    static DeliveryMethod withoutLastUpdated() {
        return builder().lastUpdated(null).build();
    }

    static DeliveryMethod smsWithValue(String value) {
        return builder().type("sms").value(value).build();
    }

    static DeliveryMethod emailWithValue(String value) {
        return builder().type("email").value(value).build();
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
                .lastUpdated(Instant.parse("2020-09-29T06:49:59.960Z"))
                .eligibility(EligibilityMother.eligible());
    }

}
