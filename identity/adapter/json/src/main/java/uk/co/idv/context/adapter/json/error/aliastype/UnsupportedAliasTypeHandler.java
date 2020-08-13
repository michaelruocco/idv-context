package uk.co.idv.context.adapter.json.error.aliastype;

import uk.co.idv.context.adapter.json.error.ApiError;
import uk.co.idv.context.adapter.json.error.handler.ErrorHandler;
import uk.co.idv.context.entities.alias.UnsupportedAliasTypeExeception;

import java.util.Optional;

public class UnsupportedAliasTypeHandler implements ErrorHandler {

    @Override
    public Optional<ApiError> apply(Throwable cause) {
        if (supports(cause)) {
            return Optional.of(toError(cause));
        }
        return Optional.empty();
    }

    private static boolean supports(Throwable cause) {
        return UnsupportedAliasTypeExeception.class.isAssignableFrom(cause.getClass());
    }

    private static ApiError toError(Throwable cause) {
        return new UnsupportedAliasTypeError(cause.getMessage());
    }

}
