package uk.co.idv.context.adapter.json.error.eligibilitynotconfigured;

import lombok.extern.slf4j.Slf4j;
import uk.co.idv.context.adapter.json.error.ApiError;
import uk.co.idv.context.adapter.json.error.handler.AbstractErrorHandler;
import uk.co.idv.identity.usecases.eligibility.EligibilityNotConfiguredException;

@Slf4j
public class EligibilityNotConfiguredHandler extends AbstractErrorHandler {

    @Override
    protected boolean supports(Throwable cause) {
        return EligibilityNotConfiguredException.class.isAssignableFrom(cause.getClass());
    }

    @Override
    protected ApiError toError(Throwable cause) {
        return new EligibilityNotConfiguredError(cause.getMessage());
    }

}
