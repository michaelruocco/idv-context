package uk.co.idv.context.adapter.json.error.policynotfound;

import lombok.extern.slf4j.Slf4j;
import uk.co.idv.context.adapter.json.error.ApiError;
import uk.co.idv.context.adapter.json.error.handler.ErrorHandler;
import uk.co.idv.context.usecases.policy.load.PolicyNotFoundException;

import java.util.Optional;

@Slf4j
public class PolicyNotFoundHandler implements ErrorHandler {

    @Override
    public Optional<ApiError> apply(Throwable cause) {
        if (supports(cause)) {
            return Optional.of(toError(cause));
        }
        return Optional.empty();
    }

    private static boolean supports(Throwable cause) {
        return PolicyNotFoundException.class.isAssignableFrom(cause.getClass());
    }

    private static ApiError toError(Throwable cause) {
        return new PolicyNotFoundError(cause.getMessage());
    }

}
