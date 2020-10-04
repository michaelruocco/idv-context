package uk.co.idv.context.entities.context.method.otp.delivery.eligibility;

import uk.co.idv.context.entities.policy.method.otp.delivery.phone.simswap.SimSwapConfigMother;
import uk.co.idv.method.entities.eligibility.Eligibility;
import uk.co.idv.method.entities.eligibility.EligibilityMother;

import java.util.concurrent.CompletableFuture;

public interface AsyncSimSwapEligibilityMother {

    static AsyncFutureSimSwapEligibility ineligible() {
        return builder().build();
    }

    static AsyncFutureSimSwapEligibility withFuture(CompletableFuture<Eligibility> future) {
        return builder().future(future).build();
    }

    static AsyncFutureSimSwapEligibility.AsyncFutureSimSwapEligibilityBuilder builder() {
        return AsyncFutureSimSwapEligibility.builder()
                .config(SimSwapConfigMother.build())
                .future(CompletableFuture.completedFuture(EligibilityMother.ineligible()));
    }

}
