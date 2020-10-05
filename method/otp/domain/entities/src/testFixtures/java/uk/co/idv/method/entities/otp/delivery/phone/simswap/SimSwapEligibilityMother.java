package uk.co.idv.context.entities.context.method.otp.delivery.eligibility;

import uk.co.idv.context.entities.policy.method.otp.delivery.phone.simswap.SimSwapConfigMother;

public interface SimSwapEligibilityMother {

    static SimSwapEligibility build() {
        return builder().build();
    }

    static SimSwapEligibility.SimSwapEligibilityBuilder builder() {
        return SimSwapEligibility.builder()
                .config(SimSwapConfigMother.build())
                .status("failure");
    }

}
