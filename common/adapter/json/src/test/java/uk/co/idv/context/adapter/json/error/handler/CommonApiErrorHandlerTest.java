package uk.co.idv.context.adapter.json.error.handler;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.adapter.json.error.ApiError;
import uk.co.idv.context.adapter.json.error.badrequest.BadRequestError;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static uk.co.idv.context.adapter.json.error.handler.JsonParseExceptionMother.buildJsonParseException;

class CommonApiErrorHandlerTest {

    private final ErrorHandler compositeHandler = new CommonApiErrorHandler();

    @Test
    void shouldHandleJsonParseExceptionAsBadRequestError() {
        Throwable exception = buildJsonParseException();

        Optional<ApiError> error = compositeHandler.apply(exception);

        assertThat(error).containsInstanceOf(BadRequestError.class);
    }

    @Test
    void shouldHandleNotAnyOtherException() {
        Throwable exception = mock(Throwable.class);

        Optional<ApiError> error = compositeHandler.apply(exception);

        assertThat(error).isEmpty();
    }

}
