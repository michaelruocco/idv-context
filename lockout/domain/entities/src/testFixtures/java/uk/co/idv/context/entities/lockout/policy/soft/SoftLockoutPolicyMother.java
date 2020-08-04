package uk.co.idv.context.entities.lockout.policy.soft;

import uk.co.idv.context.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.context.entities.lockout.policy.LockoutPolicy.LockoutPolicyBuilder;
import uk.co.idv.context.entities.lockout.policy.LockoutPolicyMother;
import uk.co.idv.context.entities.lockout.policy.recordattempt.AlwaysRecordAttemptPolicy;

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
