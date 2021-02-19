package uk.co.idv.context.adapter.json.error.contextexpired;

import lombok.extern.slf4j.Slf4j;
import uk.co.idv.common.adapter.json.error.ApiError;
import uk.co.idv.common.adapter.json.error.handler.AbstractErrorHandler;
import uk.co.idv.context.entities.context.ContextExpiredException;
import uk.co.idv.method.adapter.json.error.contextexpired.ContextExpiredError;

@Slf4j
public class ContextExpiredHandler extends AbstractErrorHandler {

    @Override
    protected boolean supports(Throwable cause) {
        return ContextExpiredException.class.isAssignableFrom(cause.getClass());
    }

    @Override
    protected ApiError toError(Throwable cause) {
        ContextExpiredException error = (ContextExpiredException) cause;
        return ContextExpiredError.builder()
                .id(error.getId())
                .expiry(error.getExpiry())
                .build();
    }

}
