package uk.co.idv.lockout.entities.policy;

import uk.co.idv.policy.entities.policy.PolicyRequestMother;

public interface NoLockoutPoliciesConfiguredExceptionMother {

    static NoLockoutPoliciesConfiguredException build() {
        return new NoLockoutPoliciesConfiguredException(PolicyRequestMother.build());
    }

}
