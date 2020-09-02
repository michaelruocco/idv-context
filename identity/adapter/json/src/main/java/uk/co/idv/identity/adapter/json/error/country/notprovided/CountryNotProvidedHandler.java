package uk.co.idv.identity.adapter.json.error.country.notprovided;

import lombok.extern.slf4j.Slf4j;
import uk.co.idv.common.adapter.json.error.ApiError;
import uk.co.idv.common.adapter.json.error.handler.ErrorHandler;
import uk.co.idv.identity.usecases.identity.create.CountryNotProvidedException;

import java.util.Optional;

@Slf4j
public class CountryNotProvidedHandler implements ErrorHandler {

    @Override
    public Optional<ApiError> apply(Throwable cause) {
        if (supports(cause)) {
            return Optional.of(toError());
        }
        return Optional.empty();
    }

    private static boolean supports(Throwable cause) {
        return CountryNotProvidedException.class.isAssignableFrom(cause.getClass());
    }

    private static ApiError toError() {
        return new CountryNotProvidedError();
    }

}
