package uk.co.idv.method.entities.otp.delivery;

import uk.co.idv.identity.entities.emailaddress.EmailAddress;
import uk.co.idv.method.entities.eligibility.Eligibility;
import uk.co.idv.method.entities.eligibility.EligibilityMother;
import uk.co.idv.method.entities.otp.delivery.phone.OtpPhoneNumber;
import uk.co.idv.method.entities.otp.delivery.phone.simswap.AsyncSimSwapEligibilityMother;

import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface DeliveryMethodMother {

    static DeliveryMethod build() {
        return builder().build();
    }

    static DeliveryMethod eligible(UUID id) {
        return builder()
                .id(id)
                .eligibility(EligibilityMother.eligible())
                .build();
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

    static DeliveryMethod smsWithValue(OtpPhoneNumber phoneNumber) {
        return builder().type("sms").value(phoneNumber.getValue()).build();
    }

    static DeliveryMethod emailWithValue(EmailAddress emailAddress) {
        return builder().type("email").value(emailAddress.getValue()).build();
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
