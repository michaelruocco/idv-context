package uk.co.idv.context.entities.policy;

import uk.co.idv.context.entities.policy.key.ChannelPolicyKeyMother;



public interface FakePolicyMother {

    static FakePolicy build() {
        return new FakePolicy(ChannelPolicyKeyMother.build());
    }

}
