package uk.co.idv.context.adapter.json.error.handler;

import lombok.RequiredArgsConstructor;
import uk.co.idv.context.adapter.json.error.ApiError;

import java.util.Arrays;
import java.util.Collection;

@RequiredArgsConstructor
public class CompositeErrorHandler implements ErrorHandler {

    private final ErrorHandler defaultHandler;
    private final Collection<ErrorHandler> handlers;

    public CompositeErrorHandler(ErrorHandler defaultHandler, ErrorHandler... handlers) {
        this(defaultHandler, Arrays.asList(handlers));
    }

    @Override
    public boolean supports(Throwable cause) {
        return true;
    }

    @Override
    public ApiError apply(Throwable cause) {
        ErrorHandler handler = findHandler(cause);
        return handler.apply(cause);
    }

    private ErrorHandler findHandler(Throwable cause) {
        return handlers.stream()
                .filter(handler -> handler.supports(cause))
                .findFirst()
                .orElse(defaultHandler);
    }

}
