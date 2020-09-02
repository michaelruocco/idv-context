package uk.co.idv.identity.adapter.json.error.handler;

import lombok.RequiredArgsConstructor;
import uk.co.idv.identity.adapter.json.error.ApiError;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

@RequiredArgsConstructor
public class CompositeErrorHandler implements ErrorHandler {

    private final Collection<ErrorHandler> handlers;

    public CompositeErrorHandler(ErrorHandler... handlers) {
        this(Arrays.asList(handlers));
    }

    @Override
    public Optional<ApiError> apply(Throwable cause) {
        return toError(cause);
    }

    private Optional<ApiError> toError(Throwable cause) {
        return handlers.stream()
                .map(handler -> handler.apply(cause))
                .flatMap(Optional::stream)
                .findFirst();
    }

}
