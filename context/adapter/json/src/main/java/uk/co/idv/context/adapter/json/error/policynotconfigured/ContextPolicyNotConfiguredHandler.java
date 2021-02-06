package uk.co.idv.context.adapter.json.error.policynotconfigured;

import lombok.extern.slf4j.Slf4j;
import uk.co.idv.common.adapter.json.error.ApiError;
import uk.co.idv.common.adapter.json.error.handler.AbstractErrorHandler;
import uk.co.idv.context.entities.policy.NoContextPoliciesConfiguredException;

@Slf4j
public class ContextPolicyNotConfiguredHandler extends AbstractErrorHandler {

    @Override
    protected boolean supports(Throwable cause) {
        return NoContextPoliciesConfiguredException.class.isAssignableFrom(cause.getClass());
    }

    @Override
    protected ApiError toError(Throwable cause) {
        NoContextPoliciesConfiguredException error = (NoContextPoliciesConfiguredException) cause;
        return new ContextPolicyNotConfiguredError(error.getRequest());
    }

}
