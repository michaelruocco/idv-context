package uk.co.idv.context.adapter.json.error.aliastype;

import uk.co.idv.context.adapter.json.error.ApiError;
import uk.co.idv.context.adapter.json.error.handler.ErrorHandler;
import uk.co.idv.context.entities.alias.UnsupportedAliasTypeExeception;

public class UnsupportedAliasTypeHandler implements ErrorHandler {

    @Override
    public boolean supports(Throwable cause) {
        return UnsupportedAliasTypeExeception.class.isAssignableFrom(cause.getClass());
    }

    @Override
    public ApiError apply(Throwable cause) {
        return new UnsupportedAliasTypeError(cause.getMessage());
    }

}
