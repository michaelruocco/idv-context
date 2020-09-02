package uk.co.idv.lockout.usecases.policy;

import uk.co.idv.policy.entities.policy.PolicyRequestMother;

public interface NoLockoutPoliciesConfiguredExceptionMother {

    static NoLockoutPoliciesConfiguredException build() {
        return new NoLockoutPoliciesConfiguredException(PolicyRequestMother.build());
    }

}
