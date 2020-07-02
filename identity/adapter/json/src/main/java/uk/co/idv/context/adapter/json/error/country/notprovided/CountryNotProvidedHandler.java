package uk.co.idv.context.adapter.json.error.country.notprovided;

import lombok.extern.slf4j.Slf4j;
import uk.co.idv.context.adapter.json.error.ApiError;
import uk.co.idv.context.adapter.json.error.handler.ErrorHandler;
import uk.co.idv.context.usecases.identity.create.CountryNotProvidedException;

@Slf4j
public class CountryNotProvidedHandler implements ErrorHandler {

    @Override
    public boolean supports(Throwable cause) {
        return CountryNotProvidedException.class.isAssignableFrom(cause.getClass());
    }

    @Override
    public ApiError apply(Throwable cause) {
        return new CountryNotProvidedError();
    }

}
