package uk.co.idv.method.entities.otp.delivery.phone.sms;

import uk.co.idv.method.entities.eligibility.Eligibility;
import uk.co.idv.method.entities.eligibility.EligibilityMother;
import uk.co.idv.method.entities.otp.delivery.DeliveryMethod;
import uk.co.idv.method.entities.otp.delivery.phone.simswap.AsyncSimSwapEligibilityMother;

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

    static DeliveryMethod.DeliveryMethodBuilder builder() {
        return DeliveryMethod.builder()
                .id(UUID.fromString("cedbf56f-66d5-41e0-a669-01559b031c7c"))
                .type("sms")
                .value("+447809123456")
                .eligibility(EligibilityMother.eligible());
    }

}
