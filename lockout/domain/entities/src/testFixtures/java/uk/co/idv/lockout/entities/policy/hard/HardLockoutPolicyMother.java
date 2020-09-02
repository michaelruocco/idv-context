package uk.co.idv.lockout.entities.policy.hard;

import uk.co.idv.lockout.entities.policy.LockoutPolicy;
import uk.co.idv.lockout.entities.policy.LockoutPolicy.LockoutPolicyBuilder;
import uk.co.idv.lockout.entities.policy.LockoutPolicyMother;
import uk.co.idv.lockout.entities.policy.recordattempt.AlwaysRecordAttemptPolicy;
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
