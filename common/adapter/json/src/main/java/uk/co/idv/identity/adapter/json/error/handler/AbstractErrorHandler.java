package uk.co.idv.identity.adapter.json.error.handler;

import uk.co.idv.identity.adapter.json.error.ApiError;

import java.util.Optional;

public abstract class AbstractErrorHandler implements ErrorHandler {

    @Override
    public Optional<ApiError> apply(Throwable cause) {
        if (supports(cause)) {
            return Optional.of(toError(cause));
        }
        return Optional.empty();
    }

    protected abstract boolean supports(Throwable cause);

    protected abstract ApiError toError(Throwable cause);

}
