package uk.co.idv.context.entities.lockout.policy.hard;

import uk.co.idv.context.entities.lockout.policy.AttemptsFilter;
import uk.co.idv.context.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.context.entities.lockout.policy.LockoutPolicy.LockoutPolicyBuilder;
import uk.co.idv.context.entities.lockout.policy.recordattempt.AlwaysRecordAttemptPolicy;
import uk.co.idv.context.entities.policy.PolicyKey;
import uk.co.idv.context.entities.policy.key.ChannelPolicyKeyMother;

public interface HardLockoutPolicyMother {

    static LockoutPolicy build() {
        return builder().build();
    }

    static LockoutPolicy withMaxNumberOfAttempts(int maxNumberOfAttempts) {
         return builder().stateCalculator(new HardLockoutStateCalculator(maxNumberOfAttempts)).build();
    }

    static LockoutPolicyBuilder builder() {
        PolicyKey key = ChannelPolicyKeyMother.defaultChannelKey();
        return LockoutPolicy.builder()
                .key(key)
                .attemptsFilter(new AttemptsFilter(key))
                .recordAttemptPolicy(new AlwaysRecordAttemptPolicy())
                .stateCalculator(new HardLockoutStateCalculator(3));
    }

}
