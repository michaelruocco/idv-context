package uk.co.idv.identity.adapter.json.error.policynotfound;

import lombok.extern.slf4j.Slf4j;
import uk.co.idv.identity.adapter.json.error.ApiError;
import uk.co.idv.identity.adapter.json.error.handler.AbstractErrorHandler;
import uk.co.idv.context.usecases.policy.load.PolicyNotFoundException;

@Slf4j
public class PolicyNotFoundHandler extends AbstractErrorHandler {

    @Override
    protected boolean supports(Throwable cause) {
        return PolicyNotFoundException.class.isAssignableFrom(cause.getClass());
    }

    @Override
    protected ApiError toError(Throwable cause) {
        return new PolicyNotFoundError(cause.getMessage());
    }

}
