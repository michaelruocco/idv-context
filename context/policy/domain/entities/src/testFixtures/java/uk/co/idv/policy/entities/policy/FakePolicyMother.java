package uk.co.idv.policy.entities.policy;

import uk.co.idv.policy.entities.policy.key.channel.ChannelPolicyKeyMother;



public interface FakePolicyMother {

    static FakePolicy build() {
        return new FakePolicy(ChannelPolicyKeyMother.build());
    }

}
