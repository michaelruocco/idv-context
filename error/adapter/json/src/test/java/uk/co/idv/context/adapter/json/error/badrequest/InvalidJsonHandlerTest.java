package uk.co.idv.context.adapter.json.error.badrequest;

import com.fasterxml.jackson.core.JsonParseException;
import org.junit.jupiter.api.Test;
import uk.co.idv.context.adapter.json.error.ApiError;
import uk.co.idv.context.adapter.json.error.handler.ErrorHandler;

import java.io.UncheckedIOException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.co.idv.context.adapter.json.error.handler.JsonParseExceptionMother.buildJsonParseException;

class InvalidJsonHandlerTest {

    private final ErrorHandler handler = new InvalidJsonHandler();

    @Test
    void shouldSupportJsonParseException() {
        JsonParseException exception = buildJsonParseException();

        assertThat(handler.apply(exception)).isPresent();
    }

    @Test
    void shouldSupportExceptionIfCauseIsJsonParseException() {
        Throwable exception = new UncheckedIOException(buildJsonParseException());

        Optional<ApiError> error = handler.apply(exception);

        assertThat(error).isPresent();
    }

    @Test
    void shouldNotSupportAnyOtherException() {
        Throwable other = new Throwable();

        Optional<ApiError> error = handler.apply(other);

        assertThat(error).isEmpty();
    }

    @Test
    void shouldReturnBadRequestError() {
        JsonParseException exception = buildJsonParseException();

        Optional<ApiError> error = handler.apply(exception);

        assertThat(error).containsInstanceOf(BadRequestError.class);
    }

}
