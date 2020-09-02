package uk.co.idv.lockout.adapter.json.error.policynotconfigured;

import lombok.extern.slf4j.Slf4j;
import uk.co.idv.identity.adapter.json.error.ApiError;
import uk.co.idv.identity.adapter.json.error.handler.AbstractErrorHandler;
import uk.co.idv.lockout.usecases.policy.NoLockoutPoliciesConfiguredException;

@Slf4j
public class LockoutPolicyNotConfiguredHandler extends AbstractErrorHandler {

    @Override
    protected boolean supports(Throwable cause) {
        return NoLockoutPoliciesConfiguredException.class.isAssignableFrom(cause.getClass());
    }

    @Override
    protected ApiError toError(Throwable cause) {
        NoLockoutPoliciesConfiguredException error = (NoLockoutPoliciesConfiguredException) cause;
        return new LockoutPolicyNotConfiguredError(error.getRequest());
    }

}
