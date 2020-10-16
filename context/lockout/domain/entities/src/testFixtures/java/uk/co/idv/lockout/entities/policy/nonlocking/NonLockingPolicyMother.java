package uk.co.idv.lockout.entities.policy.nonlocking;

import uk.co.idv.lockout.entities.policy.LockoutPolicy;
import uk.co.idv.lockout.entities.policy.LockoutPolicy.LockoutPolicyBuilder;
import uk.co.idv.lockout.entities.policy.LockoutPolicyMother;
import uk.co.idv.lockout.entities.policy.recordattempt.NeverRecordAttemptPolicy;

public interface NonLockingPolicyMother {

    static LockoutPolicy build() {
        return builder().build();
    }

    static LockoutPolicyBuilder builder() {
        return LockoutPolicyMother.builder()
                .recordAttemptPolicy(new NeverRecordAttemptPolicy())
                .stateCalculator(NonLockingStateCalculatorMother.build());
    }

}
