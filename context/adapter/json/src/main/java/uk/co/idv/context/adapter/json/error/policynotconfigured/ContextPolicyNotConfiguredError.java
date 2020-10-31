package uk.co.idv.context.adapter.json.error.policynotconfigured;

import lombok.Getter;
import uk.co.idv.policy.adapter.json.error.policynotfound.PolicyNotConfiguredError;
import uk.co.idv.policy.entities.policy.PolicyRequest;

@Getter
public class ContextPolicyNotConfiguredError extends PolicyNotConfiguredError {

    private static final String TITLE = "Context policy not configured";
    private static final String MESSAGE = "Context policy not configured for channel, activity and alias combination";

    public ContextPolicyNotConfiguredError(PolicyRequest request) {
        super(TITLE, MESSAGE, request);
    }

}
