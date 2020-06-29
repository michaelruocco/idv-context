package uk.co.idv.context.adapter.json.error.updateidvid;

import lombok.extern.slf4j.Slf4j;
import uk.co.idv.context.adapter.json.error.ApiError;
import uk.co.idv.context.adapter.json.error.handler.ErrorHandler;
import uk.co.idv.context.usecases.identity.save.CannotUpdateIdvIdException;

@Slf4j
public class CannotUpdateIdvIdHandler implements ErrorHandler {

    @Override
    public boolean supports(Throwable cause) {
        return CannotUpdateIdvIdException.class.isAssignableFrom(cause.getClass());
    }

    @Override
    public ApiError apply(Throwable cause) {
        log.error(cause.getMessage(), cause);
        CannotUpdateIdvIdException error = (CannotUpdateIdvIdException) cause;
        return CannotUpdateIdvIdError.builder()
                .updated(error.getUpdated())
                .existing(error.getExisting())
                .build();
    }

}
