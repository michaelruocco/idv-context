package uk.co.idv.context.adapter.json.error.eligibilitynotconfigured;

import lombok.extern.slf4j.Slf4j;
import uk.co.idv.context.adapter.json.error.ApiError;
import uk.co.idv.context.adapter.json.error.handler.ErrorHandler;
import uk.co.idv.context.usecases.eligibility.EligibilityNotConfiguredException;

@Slf4j
public class EligibilityNotConfiguredHandler implements ErrorHandler {

    @Override
    public boolean supports(Throwable cause) {
        return EligibilityNotConfiguredException.class.isAssignableFrom(cause.getClass());
    }

    @Override
    public ApiError apply(Throwable cause) {
        return new EligibilityNotConfiguredError(cause.getMessage());
    }

}
