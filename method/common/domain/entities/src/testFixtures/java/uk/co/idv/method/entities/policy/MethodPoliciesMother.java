package uk.co.idv.method.entities.policy;

import uk.co.idv.method.entities.method.fake.policy.FakeMethodPolicyMother;

public interface MethodPoliciesMother {

    static MethodPolicies build() {
        return with(FakeMethodPolicyMother.build());
    }

    static MethodPolicies with(MethodPolicy... methodPolicies) {
        return new MethodPolicies(methodPolicies);
    }

}
