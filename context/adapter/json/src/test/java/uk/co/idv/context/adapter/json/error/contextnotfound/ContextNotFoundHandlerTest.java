package uk.co.idv.context.adapter.json.error.contextnotfound;

import org.junit.jupiter.api.Test;
import uk.co.idv.common.adapter.json.error.ApiError;
import uk.co.idv.common.adapter.json.error.handler.ErrorHandler;
import uk.co.idv.context.entities.context.ContextNotFoundException;
import uk.co.idv.context.entities.context.ContextNotFoundExceptionMother;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class ContextNotFoundHandlerTest {

    private final ErrorHandler handler = new ContextNotFoundHandler();

    @Test
    void shouldConvertContextNotFoundExceptionToError() {
        ContextNotFoundException exception = ContextNotFoundExceptionMother.build();

        Optional<ApiError> error = handler.apply(exception);

        assertThat(error).containsInstanceOf(ContextNotFoundError.class);
    }

    @Test
    void shouldPopulateEmptyMeta() {
        ContextNotFoundException exception = ContextNotFoundExceptionMother.build();

        Optional<ApiError> error = handler.apply(exception);

        assertThat(error).isPresent();
        assertThat(error.get().getMeta()).isEmpty();
    }

    @Test
    void shouldNotSupportAnyOtherException() {
        Throwable other = new Throwable();

        Optional<ApiError> error = handler.apply(other);

        assertThat(error).isEmpty();
    }

}
