package uk.co.idv.policy.entities.policy;

import uk.co.idv.policy.entities.policy.key.PolicyKey;
import uk.co.idv.policy.entities.policy.key.channel.ChannelPolicyKeyMother;

import java.util.UUID;


public interface FakePolicyMother {

    static FakePolicy withId(UUID id) {
        return withKey(ChannelPolicyKeyMother.withId(id));
    }

    static FakePolicy build() {
        return withKey(ChannelPolicyKeyMother.build());
    }

    static FakePolicy withKey(PolicyKey key) {
        return new FakePolicy(key);
    }

}
