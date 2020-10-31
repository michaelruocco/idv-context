package uk.co.idv.lockout.adapter.json.error.policynotconfigured;

import lombok.Getter;
import uk.co.idv.policy.adapter.json.error.policynotfound.PolicyNotConfiguredError;
import uk.co.idv.policy.entities.policy.PolicyRequest;

@Getter
public class LockoutPolicyNotConfiguredError extends PolicyNotConfiguredError {

    private static final String TITLE = "Lockout policy not configured";
    private static final String MESSAGE = "Lockout policy not configured for channel, activity and alias combination";

    public LockoutPolicyNotConfiguredError(PolicyRequest request) {
        super(TITLE, MESSAGE, request);
    }

}
