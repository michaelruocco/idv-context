package uk.co.idv.context.adapter.json.error.internalserver;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.adapter.json.error.ApiError;
import uk.co.idv.context.adapter.json.error.handler.ErrorHandler;

import static org.assertj.core.api.Assertions.assertThat;

class InternalServerHandlerTest {

    private final ErrorHandler handler = new InternalServerHandler();

    @Test
    void shouldSupportAnyException() {
        Throwable throwable = new Throwable();

        assertThat(handler.supports(throwable)).isTrue();
    }

    @Test
    void shouldReturnIdentityNotFoundError() {
        Throwable throwable = new Throwable();

        ApiError error = handler.apply(throwable);

        assertThat(error).isInstanceOf(InternalServerError.class);
    }

    @Test
    void shouldPopulateMessage() {
        Throwable throwable = new Throwable("message");

        ApiError error = handler.apply(throwable);

        assertThat(error.getMessage()).isEqualTo(throwable.getMessage());
    }

}
