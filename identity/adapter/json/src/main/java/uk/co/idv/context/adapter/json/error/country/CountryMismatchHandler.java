package uk.co.idv.context.adapter.json.error.country;

import lombok.extern.slf4j.Slf4j;
import uk.co.idv.context.adapter.json.error.ApiError;
import uk.co.idv.context.adapter.json.error.handler.ErrorHandler;
import uk.co.idv.context.entities.identity.CountryMismatchException;

@Slf4j
public class CountryMismatchHandler implements ErrorHandler {

    @Override
    public boolean supports(Throwable cause) {
        return CountryMismatchException.class.isAssignableFrom(cause.getClass());
    }

    @Override
    public ApiError apply(Throwable cause) {
        log.error(cause.getMessage(), cause);
        CountryMismatchException error = (CountryMismatchException) cause;
        return CountryMismatchError.builder()
                .updated(error.getUpdated())
                .existing(error.getExisting())
                .build();
    }

}
