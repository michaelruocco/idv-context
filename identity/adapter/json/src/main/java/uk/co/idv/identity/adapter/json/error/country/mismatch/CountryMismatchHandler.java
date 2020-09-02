package uk.co.idv.identity.adapter.json.error.country.mismatch;

import lombok.extern.slf4j.Slf4j;
import uk.co.idv.common.adapter.json.error.ApiError;
import uk.co.idv.common.adapter.json.error.handler.AbstractErrorHandler;
import uk.co.idv.identity.entities.identity.CountryMismatchException;

@Slf4j
public class CountryMismatchHandler extends AbstractErrorHandler {

    @Override
    protected boolean supports(Throwable cause) {
        return CountryMismatchException.class.isAssignableFrom(cause.getClass());
    }

    @Override
    protected ApiError toError(Throwable cause) {
        log.error("country mismatch occurred", cause);
        CountryMismatchException error = (CountryMismatchException) cause;
        return CountryMismatchError.builder()
                .updated(error.getUpdated())
                .existing(error.getExisting())
                .build();
    }

}
