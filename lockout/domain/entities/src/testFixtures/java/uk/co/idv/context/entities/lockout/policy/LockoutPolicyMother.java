package uk.co.idv.context.entities.lockout.policy;

import uk.co.idv.context.entities.lockout.policy.LockoutPolicy.LockoutPolicyBuilder;
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
