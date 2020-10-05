package uk.co.idv.context.entities.policy.method;

import uk.co.idv.method.entities.fake.policy.FakeMethodPolicyMother;
import uk.co.idv.method.entities.policy.MethodPolicy;

public interface MethodPoliciesMother {

    static MethodPolicies build() {
        return with(FakeMethodPolicyMother.build());
    }

    static MethodPolicies with(MethodPolicy... methodPolicies) {
        return new MethodPolicies(methodPolicies);
    }

}
