package uk.co.idv.context.adapter.json.error.contextnotfound;

import lombok.extern.slf4j.Slf4j;
import uk.co.idv.common.adapter.json.error.ApiError;
import uk.co.idv.common.adapter.json.error.handler.AbstractErrorHandler;
import uk.co.idv.context.entities.context.ContextNotFoundException;

@Slf4j
public class ContextNotFoundHandler extends AbstractErrorHandler {

    @Override
    protected boolean supports(Throwable cause) {
        return ContextNotFoundException.class.isAssignableFrom(cause.getClass());
    }

    @Override
    protected ApiError toError(Throwable cause) {
        ContextNotFoundException error = (ContextNotFoundException) cause;
        return new ContextNotFoundError(error.getMessage());
    }

}
