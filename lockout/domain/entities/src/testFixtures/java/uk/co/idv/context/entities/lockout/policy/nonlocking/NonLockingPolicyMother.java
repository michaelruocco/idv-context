package uk.co.idv.context.entities.lockout.policy.nonlocking;

import uk.co.idv.context.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.context.entities.lockout.policy.LockoutPolicy.LockoutPolicyBuilder;
import uk.co.idv.context.entities.lockout.policy.LockoutPolicyMother;
import uk.co.idv.context.entities.lockout.policy.recordattempt.NeverRecordAttemptPolicy;

public interface NonLockingPolicyMother {

    static LockoutPolicy build() {
        return builder().build();
    }

    static LockoutPolicyBuilder builder() {
        return LockoutPolicyMother.builder()
                .recordAttemptPolicy(new NeverRecordAttemptPolicy())
                .stateCalculator(new NonLockingStateCalculator());
    }

}
