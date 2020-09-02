package uk.co.idv.identity.adapter.json.error.aliastype;

import uk.co.idv.identity.adapter.json.error.ApiError;
import uk.co.idv.identity.adapter.json.error.handler.AbstractErrorHandler;
import uk.co.idv.identity.entities.alias.UnsupportedAliasTypeException;

public class UnsupportedAliasTypeHandler extends AbstractErrorHandler {

    @Override
    protected boolean supports(Throwable cause) {
        return UnsupportedAliasTypeException.class.isAssignableFrom(cause.getClass());
    }

    @Override
    protected ApiError toError(Throwable cause) {
        return new UnsupportedAliasTypeError(cause.getMessage());
    }

}
