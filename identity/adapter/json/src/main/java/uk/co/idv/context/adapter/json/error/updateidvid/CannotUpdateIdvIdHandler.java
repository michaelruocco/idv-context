package uk.co.idv.context.adapter.json.error.updateidvid;

import lombok.extern.slf4j.Slf4j;
import uk.co.idv.context.adapter.json.error.ApiError;
import uk.co.idv.context.adapter.json.error.handler.ErrorHandler;
import uk.co.idv.context.usecases.identity.save.CannotUpdateIdvIdException;

import java.util.Optional;

@Slf4j
public class CannotUpdateIdvIdHandler implements ErrorHandler {

    @Override
    public Optional<ApiError> apply(Throwable cause) {
        if (supports(cause)) {
            return Optional.of(toError(cause));
        }
        return Optional.empty();
    }

    private static boolean supports(Throwable cause) {
        return CannotUpdateIdvIdException.class.isAssignableFrom(cause.getClass());
    }

    private static ApiError toError(Throwable cause) {
        log.error("cannot update idv id", cause);
        CannotUpdateIdvIdException error = (CannotUpdateIdvIdException) cause;
        return CannotUpdateIdvIdError.builder()
                .updated(error.getUpdated())
                .existing(error.getExisting())
                .build();
    }

}
