package uk.co.idv.context.entities.policy;

import uk.co.idv.context.entities.policy.method.MethodPolicies;

public interface ContextPolicyMother {

    static ContextPolicy build() {
        return new ContextPolicy(new MethodPolicies());
    }

}
