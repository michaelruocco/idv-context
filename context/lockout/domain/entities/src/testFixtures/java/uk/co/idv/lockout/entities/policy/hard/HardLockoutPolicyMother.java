package uk.co.idv.lockout.entities.policy.hard;

import uk.co.idv.lockout.entities.policy.AttemptsFilter;
import uk.co.idv.lockout.entities.policy.LockoutPolicy;
import uk.co.idv.lockout.entities.policy.LockoutPolicy.LockoutPolicyBuilder;
import uk.co.idv.lockout.entities.policy.LockoutPolicyMother;
import uk.co.idv.lockout.entities.policy.LockoutStateCalculator;
import uk.co.idv.lockout.entities.policy.RecordAttemptPolicy;
import uk.co.idv.lockout.entities.policy.recordattempt.AlwaysRecordAttemptPolicy;
import uk.co.idv.policy.entities.policy.key.ChannelPolicyKeyMother;
import uk.co.idv.policy.entities.policy.key.PolicyKey;

import java.util.UUID;

public interface HardLockoutPolicyMother {

    static LockoutPolicy build() {
        return builder().build();
    }

    static LockoutPolicy withId(UUID id) {
        return withKey(ChannelPolicyKeyMother.withId(id));
    }

    static LockoutPolicy withKey(PolicyKey key) {
        return builder()
                .key(key)
                .attemptsFilter(new AttemptsFilter(key))
                .build();
    }

    static LockoutPolicy withStateCalculator(LockoutStateCalculator stateCalculator) {
        return builder()
                .stateCalculator(stateCalculator)
                .build();
    }

    static LockoutPolicy withMaxNumberOfAttempts(int maxNumberOfAttempts) {
        return builder()
                .stateCalculator(HardLockoutStateCalculatorMother.withMaxNumberOfAttempts(maxNumberOfAttempts))
                .build();
    }

    static LockoutPolicy withRecordAttemptPolicy(RecordAttemptPolicy recordAttemptPolicy) {
        return builder()
                .recordAttemptPolicy(recordAttemptPolicy)
                .build();
    }

    static LockoutPolicyBuilder builder() {
        return LockoutPolicyMother.builder()
                .recordAttemptPolicy(new AlwaysRecordAttemptPolicy())
                .stateCalculator(HardLockoutStateCalculatorMother.build());
    }

}
