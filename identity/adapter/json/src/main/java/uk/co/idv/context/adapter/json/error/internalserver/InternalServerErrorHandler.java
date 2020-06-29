package uk.co.idv.context.adapter.json.error.internalserver;

import lombok.extern.slf4j.Slf4j;
import uk.co.idv.context.adapter.json.error.ApiError;
import uk.co.idv.context.adapter.json.error.handler.ErrorHandler;

@Slf4j
public class InternalServerErrorHandler implements ErrorHandler {

    @Override
    public boolean supports(Throwable cause) {
        return true;
    }

    @Override
    public ApiError apply(Throwable cause) {
        log.error(cause.getMessage(), cause);
        return new InternalServerError(cause.getMessage());
    }

}
