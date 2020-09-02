package uk.co.idv.lockout.entities.policy.soft;

import uk.co.idv.lockout.entities.policy.LockoutPolicy;
import uk.co.idv.lockout.entities.policy.LockoutPolicy.LockoutPolicyBuilder;
import uk.co.idv.lockout.entities.policy.LockoutPolicyMother;
import uk.co.idv.lockout.entities.policy.recordattempt.AlwaysRecordAttemptPolicy;

public interface SoftLockoutPolicyMother {

    static LockoutPolicy build() {
        return builder().build();
    }

    static LockoutPolicyBuilder builder() {
        return LockoutPolicyMother.builder()
                .recordAttemptPolicy(new AlwaysRecordAttemptPolicy())
                .stateCalculator(SoftLockoutStateCalculatorMother.build());
    }

}
