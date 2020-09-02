package uk.co.idv.common.adapter.json.error.badrequest;

import com.fasterxml.jackson.core.JsonParseException;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.common.adapter.json.error.ApiError;
import uk.co.idv.common.adapter.json.error.handler.ErrorHandler;

import java.util.Optional;

@Slf4j
public class InvalidJsonHandler implements ErrorHandler {

    @Override
    public Optional<ApiError> apply(Throwable throwable) {
        if (supports(throwable)) {
            return Optional.of(toError(throwable));
        }
        return Optional.empty();
    }

    private static boolean supports(Throwable throwable) {
        return Optional.ofNullable(throwable.getCause())
                .map(InvalidJsonHandler::isJsonParseException)
                .orElse(isJsonParseException(throwable));
    }

    private static boolean isJsonParseException(Throwable throwable) {
        return JsonParseException.class.isAssignableFrom(throwable.getClass());
    }

    private static ApiError toError(Throwable cause) {
        String message = "could not parse json";
        log.error(message, cause);
        return new BadRequestError(message);
    }

}
