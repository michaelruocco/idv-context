package uk.co.idv.context.entities.lockout.policy.hard;

import uk.co.idv.context.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.context.entities.policy.PolicyKey;
import uk.co.idv.context.entities.policy.key.ChannelPolicyKeyMother;

public interface HardLockoutPolicyMother {

    static LockoutPolicy hard() {
        return withKey(ChannelPolicyKeyMother.defaultChannelKey());
    }

    static LockoutPolicy withMaxNumberOfAttempts(int maxNumberOfAttempts) {
        return new HardLockoutPolicy(ChannelPolicyKeyMother.defaultChannelKey(), maxNumberOfAttempts);
    }

    static LockoutPolicy withKey(PolicyKey key) {
        return new HardLockoutPolicy(key, 3);
    }

}
