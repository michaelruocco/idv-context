package uk.co.idv.lockout.usecases.policy;

import uk.co.idv.context.entities.policy.PolicyRequestMother;

public interface NoLockoutPoliciesConfiguredExceptionMother {

    static NoLockoutPoliciesConfiguredException build() {
        return new NoLockoutPoliciesConfiguredException(PolicyRequestMother.build());
    }

}
