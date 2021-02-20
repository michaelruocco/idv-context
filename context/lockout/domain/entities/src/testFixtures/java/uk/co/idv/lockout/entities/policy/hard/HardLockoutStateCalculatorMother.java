package uk.co.idv.lockout.entities.policy.hard;


import uk.co.idv.lockout.entities.policy.includeattempt.IncludeAllAttemptsPolicy;
import uk.co.idv.lockout.entities.policy.includeattempt.IncludeAttemptsPolicy;

public interface HardLockoutStateCalculatorMother {

    static HardLockoutStateCalculator build() {
        return builder().build();
    }

    static HardLockoutStateCalculator withIncludeAttemptsPolicy(IncludeAttemptsPolicy includeAttemptsPolicy) {
        return builder().includeAttemptsPolicy(includeAttemptsPolicy).build();
    }

    static HardLockoutStateCalculator withMaxNumberOfAttempts(int maxNumberOfAttempts) {
         return builder().maxNumberOfAttempts(maxNumberOfAttempts).build();
    }

    static HardLockoutStateCalculator.HardLockoutStateCalculatorBuilder builder() {
        return HardLockoutStateCalculator.builder()
                .includeAttemptsPolicy(new IncludeAllAttemptsPolicy())
                .maxNumberOfAttempts(3);
    }

}
