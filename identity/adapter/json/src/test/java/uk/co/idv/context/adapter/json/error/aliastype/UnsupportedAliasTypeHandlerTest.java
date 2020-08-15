package uk.co.idv.context.adapter.json.error.aliastype;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.adapter.json.error.ApiError;
import uk.co.idv.context.adapter.json.error.handler.ErrorHandler;
import uk.co.idv.context.entities.alias.UnsupportedAliasTypeExceptionMother;
import uk.co.idv.context.entities.alias.UnsupportedAliasTypeException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class UnsupportedAliasTypeHandlerTest {

    private final ErrorHandler handler = new UnsupportedAliasTypeHandler();

    @Test
    void shouldConvertCannotUpdateIdvIdExceptionToError() {
        UnsupportedAliasTypeException exception = mock(UnsupportedAliasTypeException.class);

        Optional<ApiError> error = handler.apply(exception);

        assertThat(error).containsInstanceOf(UnsupportedAliasTypeError.class);
    }

    @Test
    void shouldPopulateErrorMessageWithExceptionMessage() {
        UnsupportedAliasTypeException exception = UnsupportedAliasTypeExceptionMother.build();

        Optional<ApiError> error = handler.apply(exception);

        assertThat(error).get().hasFieldOrPropertyWithValue("message", exception.getMessage());
    }

    @Test
    void shouldNotSupportAnyOtherException() {
        Throwable other = new Throwable();

        Optional<ApiError> error = handler.apply(other);

        assertThat(error).isEmpty();
    }

}
