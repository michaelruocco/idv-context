package uk.co.idv.context.adapter.json.error.contextexpired;

import org.junit.jupiter.api.Test;
import uk.co.idv.common.adapter.json.error.ApiError;
import uk.co.idv.common.adapter.json.error.handler.ErrorHandler;
import uk.co.idv.context.usecases.context.ContextExpiredException;
import uk.co.idv.context.usecases.context.ContextExpiredExceptionMother;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class ContextExpiredHandlerTest {

    private final ErrorHandler handler = new ContextExpiredHandler();

    @Test
    void shouldConvertCannotUpdateIdvIdExceptionToError() {
        ContextExpiredException cause = ContextExpiredExceptionMother.build();

        Optional<ApiError> error = handler.apply(cause);

        assertThat(error).containsInstanceOf(ContextExpiredError.class);
    }

    @Test
    void shouldPopulateId() {
        ContextExpiredException exception = ContextExpiredExceptionMother.build();

        Optional<ApiError> error = handler.apply(exception);

        assertThat(error).map(e -> (ContextExpiredError) e)
                .map(ContextExpiredError::getId)
                .contains(exception.getId());
    }

    @Test
    void shouldPopulateExpiry() {
        ContextExpiredException exception = ContextExpiredExceptionMother.build();

        Optional<ApiError> error = handler.apply(exception);

        assertThat(error).map(e -> (ContextExpiredError) e)
                .map(ContextExpiredError::getExpiry)
                .contains(exception.getExpiry());
    }

    @Test
    void shouldNotSupportAnyOtherException() {
        Throwable other = new Throwable();

        Optional<ApiError> error = handler.apply(other);

        assertThat(error).isEmpty();
    }

}
