package uk.co.idv.context.adapter.json.lockout.error.policynotconfigured;

import lombok.extern.slf4j.Slf4j;
import uk.co.idv.context.adapter.json.error.ApiError;
import uk.co.idv.context.adapter.json.error.handler.AbstractErrorHandler;
import uk.co.idv.context.usecases.lockout.policy.NoLockoutPoliciesConfiguredException;

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
