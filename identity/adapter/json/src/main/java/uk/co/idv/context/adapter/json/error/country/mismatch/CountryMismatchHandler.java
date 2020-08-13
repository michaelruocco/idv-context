package uk.co.idv.context.adapter.json.error.country.mismatch;

import lombok.extern.slf4j.Slf4j;
import uk.co.idv.context.adapter.json.error.ApiError;
import uk.co.idv.context.adapter.json.error.handler.ErrorHandler;
import uk.co.idv.context.entities.identity.CountryMismatchException;

import java.util.Optional;

@Slf4j
public class CountryMismatchHandler implements ErrorHandler {

    @Override
    public Optional<ApiError> apply(Throwable cause) {
        if (supports(cause)) {
            return Optional.of(toError(cause));
        }
        return Optional.empty();
    }

    private static boolean supports(Throwable cause) {
        return CountryMismatchException.class.isAssignableFrom(cause.getClass());
    }

    private static ApiError toError(Throwable cause) {
        log.error("country mismatch occurred", cause);
        CountryMismatchException error = (CountryMismatchException) cause;
        return CountryMismatchError.builder()
                .updated(error.getUpdated())
                .existing(error.getExisting())
                .build();
    }

}
