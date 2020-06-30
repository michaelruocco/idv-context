package uk.co.idv.context.adapter.json.error.identitynotfound;

import lombok.extern.slf4j.Slf4j;
import uk.co.idv.context.adapter.json.error.ApiError;
import uk.co.idv.context.adapter.json.error.handler.ErrorHandler;
import uk.co.idv.context.usecases.identity.find.IdentityNotFoundException;

@Slf4j
public class IdentityNotFoundHandler implements ErrorHandler {

    @Override
    public boolean supports(Throwable cause) {
        return IdentityNotFoundException.class.isAssignableFrom(cause.getClass());
    }

    @Override
    public ApiError apply(Throwable cause) {
        IdentityNotFoundException error = (IdentityNotFoundException) cause;
        return new IdentityNotFoundError(error.getAliases());
    }

}
