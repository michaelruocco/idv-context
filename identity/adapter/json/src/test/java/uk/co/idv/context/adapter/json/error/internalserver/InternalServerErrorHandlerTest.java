package uk.co.idv.context.adapter.json.error.internalserver;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.adapter.json.error.ApiError;
import uk.co.idv.context.adapter.json.error.handler.ErrorHandler;

import static org.assertj.core.api.Assertions.assertThat;

class InternalServerErrorHandlerTest {

    private final ErrorHandler handler = new InternalServerErrorHandler();

    @Test
    void shouldSupportAnyThrowable() {
        Throwable any = new Throwable();

        assertThat(handler.supports(any)).isTrue();
    }

    @Test
    public void shouldReturnInternalServerError() {
        Throwable cause = new Throwable();

        ApiError error = handler.apply(cause);

        assertThat(error).isInstanceOf(InternalServerError.class);
    }

    @Test
    public void shouldReturnInternalServerErrorWithCauseMessage() {
        String expectedMessage = "my-message";
        Throwable cause = givenCauseWithMessage(expectedMessage);

        ApiError error = handler.apply(cause);

        assertThat(error.getMessage()).isEqualTo(expectedMessage);
    }

    private static Throwable givenCauseWithMessage(String message) {
        return new Throwable(message);
    }

}
