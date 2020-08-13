package uk.co.idv.context.adapter.json.error.policynotfound;

import lombok.extern.slf4j.Slf4j;
import uk.co.idv.context.adapter.json.error.ApiError;
import uk.co.idv.context.adapter.json.error.handler.ErrorHandler;
import uk.co.idv.context.usecases.policy.load.PolicyNotFoundException;

@Slf4j
public class PolicyNotFoundHandler implements ErrorHandler {

    @Override
    public boolean supports(Throwable cause) {
        return PolicyNotFoundException.class.isAssignableFrom(cause.getClass());
    }

    @Override
    public ApiError apply(Throwable cause) {
        return new PolicyNotFoundError(cause.getMessage());
    }

}
