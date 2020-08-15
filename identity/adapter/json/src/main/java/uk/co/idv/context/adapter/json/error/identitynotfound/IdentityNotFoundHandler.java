package uk.co.idv.context.adapter.json.error.identitynotfound;

import lombok.extern.slf4j.Slf4j;
import uk.co.idv.context.adapter.json.error.ApiError;
import uk.co.idv.context.adapter.json.error.handler.AbstractErrorHandler;
import uk.co.idv.context.usecases.identity.find.IdentityNotFoundException;

@Slf4j
public class IdentityNotFoundHandler extends AbstractErrorHandler {

    @Override
    protected boolean supports(Throwable cause) {
        return IdentityNotFoundException.class.isAssignableFrom(cause.getClass());
    }

    @Override
    protected ApiError toError(Throwable cause) {
        IdentityNotFoundException error = (IdentityNotFoundException) cause;
        return new IdentityNotFoundError(error.getAliases());
    }

}
