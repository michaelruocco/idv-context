package uk.co.idv.method.entities.otp.delivery.phone.simswap;

import uk.co.idv.method.entities.otp.policy.delivery.phone.simswap.SimSwapConfigMother;
import uk.co.idv.method.entities.otp.simswap.eligibility.SimSwapEligibility;

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
