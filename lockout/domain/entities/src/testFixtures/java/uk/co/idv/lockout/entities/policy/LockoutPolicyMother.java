package uk.co.idv.lockout.entities.policy;

import uk.co.idv.lockout.entities.policy.LockoutPolicy.LockoutPolicyBuilder;
import uk.co.idv.context.entities.policy.PolicyKey;
import uk.co.idv.context.entities.policy.key.ChannelPolicyKeyMother;

public interface LockoutPolicyMother {

    static LockoutPolicy build() {
        return builder().build();
    }

    static LockoutPolicyBuilder builder() {
        PolicyKey key = ChannelPolicyKeyMother.build();
        return LockoutPolicy.builder()
                .key(key)
                .attemptsFilter(new AttemptsFilter(key));
    }

}
