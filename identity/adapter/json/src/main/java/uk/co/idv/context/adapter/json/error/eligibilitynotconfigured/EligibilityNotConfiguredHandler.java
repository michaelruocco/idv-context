package uk.co.idv.context.adapter.json.error.eligibilitynotconfigured;

import lombok.extern.slf4j.Slf4j;
import uk.co.idv.context.adapter.json.error.ApiError;
import uk.co.idv.context.adapter.json.error.handler.ErrorHandler;
import uk.co.idv.context.usecases.eligibility.EligibilityNotConfiguredException;

import java.util.Optional;

@Slf4j
public class EligibilityNotConfiguredHandler implements ErrorHandler {

    @Override
    public Optional<ApiError> apply(Throwable cause) {
        if (supports(cause)) {
            return Optional.of(toError(cause));
        }
        return Optional.empty();
    }

    private static boolean supports(Throwable cause) {
        return EligibilityNotConfiguredException.class.isAssignableFrom(cause.getClass());
    }

    private ApiError toError(Throwable cause) {
        return new EligibilityNotConfiguredError(cause.getMessage());
    }

}
