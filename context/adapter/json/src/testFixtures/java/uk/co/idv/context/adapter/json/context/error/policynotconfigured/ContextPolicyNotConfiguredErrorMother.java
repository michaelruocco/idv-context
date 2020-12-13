package uk.co.idv.context.adapter.json.context.error.policynotconfigured;

import uk.co.idv.context.adapter.json.error.policynotconfigured.ContextPolicyNotConfiguredError;
import uk.co.idv.policy.entities.policy.PolicyRequestMother;

public interface ContextPolicyNotConfiguredErrorMother {

    static ContextPolicyNotConfiguredError contextPolicyNotConfiguredError() {
        return new ContextPolicyNotConfiguredError(PolicyRequestMother.build());
    }

}
