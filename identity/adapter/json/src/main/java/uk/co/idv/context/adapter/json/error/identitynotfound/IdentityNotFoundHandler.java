package uk.co.idv.context.adapter.json.error.identitynotfound;

import lombok.extern.slf4j.Slf4j;
import uk.co.idv.context.adapter.json.error.ApiError;
import uk.co.idv.context.adapter.json.error.handler.ErrorHandler;
import uk.co.idv.context.usecases.identity.find.IdentityNotFoundException;

import java.util.Optional;

@Slf4j
public class IdentityNotFoundHandler implements ErrorHandler {

    @Override
    public Optional<ApiError> apply(Throwable cause) {
        if (supports(cause)) {
            return Optional.of(toError(cause));
        }
        return Optional.empty();
    }

    private static boolean supports(Throwable cause) {
        return IdentityNotFoundException.class.isAssignableFrom(cause.getClass());
    }

    private static ApiError toError(Throwable cause) {
        IdentityNotFoundException error = (IdentityNotFoundException) cause;
        return new IdentityNotFoundError(error.getAliases());
    }

}
