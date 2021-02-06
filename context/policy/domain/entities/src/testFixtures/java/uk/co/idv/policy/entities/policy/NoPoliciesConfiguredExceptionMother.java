package uk.co.idv.policy.entities.policy;

public interface NoPoliciesConfiguredExceptionMother {

    static NoPoliciesConfiguredException build() {
        return new NoPoliciesConfiguredException(PolicyRequestMother.build());
    }

}
