package uk.co.idv.context.adapter.json.error.updateidvid;

import lombok.extern.slf4j.Slf4j;
import uk.co.idv.context.adapter.json.error.ApiError;
import uk.co.idv.context.adapter.json.error.handler.AbstractErrorHandler;
import uk.co.idv.context.usecases.identity.save.CannotUpdateIdvIdException;

@Slf4j
public class CannotUpdateIdvIdHandler extends AbstractErrorHandler {

    @Override
    protected boolean supports(Throwable cause) {
        return CannotUpdateIdvIdException.class.isAssignableFrom(cause.getClass());
    }

    @Override
    protected ApiError toError(Throwable cause) {
        log.error("cannot update idv id", cause);
        CannotUpdateIdvIdException error = (CannotUpdateIdvIdException) cause;
        return CannotUpdateIdvIdError.builder()
                .updated(error.getUpdated())
                .existing(error.getExisting())
                .build();
    }

}
