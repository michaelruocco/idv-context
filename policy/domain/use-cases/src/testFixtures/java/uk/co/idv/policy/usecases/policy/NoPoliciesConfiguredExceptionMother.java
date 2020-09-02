package uk.co.idv.policy.usecases.policy;

import uk.co.idv.policy.entities.policy.PolicyRequestMother;

public interface NoPoliciesConfiguredExceptionMother {

    static NoPoliciesConfiguredException build() {
        return new NoPoliciesConfiguredException(PolicyRequestMother.build());
    }

}
