package uk.co.idv.context.entities.policy.method;

import uk.co.idv.context.entities.policy.method.otp.OtpPolicyMother;

public interface MethodPoliciesMother {

    static MethodPolicies build() {
        return new MethodPolicies(OtpPolicyMother.build());
    }

}
