package uk.co.idv.context.usecases.policy;

import uk.co.idv.context.entities.policy.PolicyRequestMother;

public interface NoPoliciesConfiguredExceptionMother {

    static NoPoliciesConfiguredException build() {
        return new NoPoliciesConfiguredException(PolicyRequestMother.build());
    }

}
