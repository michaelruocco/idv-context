package uk.co.idv.context.adapter.json.error.notnextmethod;

import org.junit.jupiter.api.Test;
import uk.co.idv.common.adapter.json.error.ApiError;
import uk.co.idv.common.adapter.json.error.handler.ErrorHandler;
import uk.co.idv.context.entities.context.NotNextMethodException;
import uk.co.idv.context.entities.context.NotNextMethodExceptionMother;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class NotNextMethodErrorHandlerTest {

    private final ErrorHandler handler = new NotNextMethodHandler();

    @Test
    void shouldConvertNotNextMethodExceptionNotNextMethodExceptionToError() {
        NotNextMethodException exception = NotNextMethodExceptionMother.build();

        Optional<ApiError> error = handler.apply(exception);

        assertThat(error).containsInstanceOf(NotNextMethodError.class);
    }

    @Test
    void shouldNotSupportAnyOtherException() {
        Throwable other = new Throwable();

        Optional<ApiError> error = handler.apply(other);

        assertThat(error).isEmpty();
    }

}
