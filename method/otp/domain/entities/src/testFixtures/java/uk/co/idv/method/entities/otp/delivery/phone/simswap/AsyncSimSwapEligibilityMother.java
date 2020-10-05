package uk.co.idv.method.entities.otp.delivery.phone.simswap;

import uk.co.idv.method.entities.eligibility.Eligibility;
import uk.co.idv.method.entities.eligibility.EligibilityMother;
import uk.co.idv.method.entities.otp.policy.delivery.phone.simswap.SimSwapConfigMother;
import uk.co.idv.method.entities.otp.simswap.eligibility.AsyncFutureSimSwapEligibility;

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
