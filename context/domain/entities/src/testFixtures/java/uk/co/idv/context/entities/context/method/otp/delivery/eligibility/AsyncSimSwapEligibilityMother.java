package uk.co.idv.context.entities.context.method.otp.delivery.eligibility;

import uk.co.idv.context.entities.context.eligibility.EligibilityMother;
import uk.co.idv.context.entities.policy.method.otp.delivery.phone.simswap.SimSwapConfigMother;

import java.util.concurrent.CompletableFuture;

public interface AsyncSimSwapEligibilityMother {

    static AsyncSimSwapEligibility ineligible() {
        return builder().build();
    }

    static AsyncSimSwapEligibility.AsyncSimSwapEligibilityBuilder builder() {
        return AsyncSimSwapEligibility.builder()
                .config(SimSwapConfigMother.build())
                .future(CompletableFuture.completedFuture(EligibilityMother.ineligible()));
    }

}
