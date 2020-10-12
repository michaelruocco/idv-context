package uk.co.idv.policy.entities.policy;

import java.util.Arrays;

public interface PoliciesMother {

    static Policies<Policy> singleFakePolicy() {
        return with(FakePolicyMother.build());
    }

    static Policies<Policy> with(Policy... policies) {
        return new Policies<>(Arrays.asList(policies));
    }

}
