package uk.co.idv.context.entities.lockout.policy.hard;

import uk.co.idv.context.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.context.entities.lockout.policy.LockoutPolicy.LockoutPolicyBuilder;
import uk.co.idv.context.entities.lockout.policy.LockoutPolicyMother;
import uk.co.idv.context.entities.lockout.policy.recordattempt.AlwaysRecordAttemptPolicy;
import uk.co.idv.context.entities.policy.key.ChannelPolicyKeyMother;

import java.util.UUID;

public interface HardLockoutPolicyMother {

    static LockoutPolicy build() {
        return builder().build();
    }

    static LockoutPolicy withId(UUID id) {
        return builder()
                .key(ChannelPolicyKeyMother.withId(id))
                .build();
    }

    static LockoutPolicy withMaxNumberOfAttempts(int maxNumberOfAttempts) {
        return builder()
                .stateCalculator(HardLockoutStateCalculatorMother.withMaxNumberOfAttempts(maxNumberOfAttempts))
                .build();
    }

    static LockoutPolicyBuilder builder() {
        return LockoutPolicyMother.builder()
                .recordAttemptPolicy(new AlwaysRecordAttemptPolicy())
                .stateCalculator(HardLockoutStateCalculatorMother.build());
    }

}
