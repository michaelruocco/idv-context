package uk.co.idv.context.adapter.json.error.notnextmethod;

import lombok.extern.slf4j.Slf4j;
import uk.co.idv.common.adapter.json.error.ApiError;
import uk.co.idv.common.adapter.json.error.handler.AbstractErrorHandler;
import uk.co.idv.context.usecases.context.result.NotNextMethodException;

@Slf4j
public class NotNextMethodHandler extends AbstractErrorHandler {

    @Override
    protected boolean supports(Throwable cause) {
        return NotNextMethodException.class.isAssignableFrom(cause.getClass());
    }

    @Override
    protected ApiError toError(Throwable cause) {
        NotNextMethodException error = (NotNextMethodException) cause;
        return new NotNextMethodError(error.getMessage());
    }

}
