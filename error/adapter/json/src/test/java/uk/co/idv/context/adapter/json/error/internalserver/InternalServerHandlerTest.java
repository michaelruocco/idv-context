package uk.co.idv.context.adapter.json.error.internalserver;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.adapter.json.error.ApiError;
import uk.co.idv.context.adapter.json.error.handler.ErrorHandler;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class InternalServerHandlerTest {

    private final ErrorHandler handler = new InternalServerHandler();

    @Test
    void shouldReturnInternalServerError() {
        Throwable throwable = new Throwable();

        Optional<ApiError> error = handler.apply(throwable);

        assertThat(error).containsInstanceOf(InternalServerError.class);
    }

    @Test
    void shouldPopulateMessage() {
        Throwable throwable = new Throwable("message");

        Optional<ApiError> error = handler.apply(throwable);

        assertThat(error).isPresent();
        assertThat(error.get().getMessage()).isEqualTo(throwable.getMessage());
    }

}
