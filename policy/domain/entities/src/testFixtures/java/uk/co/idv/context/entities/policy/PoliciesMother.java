package uk.co.idv.context.entities.policy;

import uk.co.idv.context.entities.policy.key.ChannelPolicyKeyMother;

public interface PoliciesMother {

    static Policies<Policy> fake() {
        return new Policies<>(new FakePolicy(ChannelPolicyKeyMother.build()));
    }

}
