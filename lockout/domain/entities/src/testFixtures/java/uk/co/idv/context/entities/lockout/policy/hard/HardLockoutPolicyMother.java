package uk.co.idv.context.entities.lockout.policy.hard;

import uk.co.idv.context.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.context.entities.lockout.policy.LockoutPolicy.LockoutPolicyBuilder;
import uk.co.idv.context.entities.lockout.policy.LockoutPolicyMother;
import uk.co.idv.context.entities.lockout.policy.recordattempt.AlwaysRecordAttemptPolicy;

public interface HardLockoutPolicyMother {

    static LockoutPolicy build() {
        return builder().build();
    }

    static LockoutPolicy withMaxNumberOfAttempts(int maxNumberOfAttempts) {
         return builder().stateCalculator(new HardLockoutStateCalculator(maxNumberOfAttempts)).build();
    }

    static LockoutPolicyBuilder builder() {
        return LockoutPolicyMother.builder()
                .recordAttemptPolicy(new AlwaysRecordAttemptPolicy())
                .stateCalculator(new HardLockoutStateCalculator(3));
    }

}
