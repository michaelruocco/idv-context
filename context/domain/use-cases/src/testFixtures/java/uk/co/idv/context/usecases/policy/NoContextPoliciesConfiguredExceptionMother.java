package uk.co.idv.context.usecases.policy;

import uk.co.idv.policy.entities.policy.PolicyRequestMother;

public interface NoContextPoliciesConfiguredExceptionMother {

    static NoContextPoliciesConfiguredException build() {
        return new NoContextPoliciesConfiguredException(PolicyRequestMother.build());
    }

}
