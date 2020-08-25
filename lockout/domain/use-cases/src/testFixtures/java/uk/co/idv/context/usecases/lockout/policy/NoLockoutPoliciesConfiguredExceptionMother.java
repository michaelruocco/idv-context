package uk.co.idv.context.usecases.lockout.policy;

import uk.co.idv.context.entities.policy.PolicyRequestMother;

public interface NoLockoutPoliciesConfiguredExceptionMother {

    static NoLockoutPoliciesConfiguredException build() {
        return new NoLockoutPoliciesConfiguredException(PolicyRequestMother.build());
    }

}
