package uk.co.idv.context.adapter.json.error.aliastype;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.adapter.json.error.ApiError;
import uk.co.idv.context.adapter.json.error.handler.ErrorHandler;
import uk.co.idv.context.entities.alias.UnsupportedAliasTypeExeception;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class UnsupportedAliasTypeHandlerTest {

    private final ErrorHandler handler = new UnsupportedAliasTypeHandler();

    @Test
    void shouldSupportCannotUpdateIdvIdException() {
        UnsupportedAliasTypeExeception exception = mock(UnsupportedAliasTypeExeception.class);

        assertThat(handler.apply(exception)).isPresent();
    }

    @Test
    void shouldNotSupportAnyOtherException() {
        Throwable other = new Throwable();

        assertThat(handler.apply(other)).isEmpty();
    }

    @Test
    void shouldReturnUnsupportedAliasTypeError() {
        UnsupportedAliasTypeExeception cause = mock(UnsupportedAliasTypeExeception.class);

        Optional<ApiError> error = handler.apply(cause);

        assertThat(error).containsInstanceOf(UnsupportedAliasTypeError.class);
    }

    @Test
    void shouldPopulateMessageWithAliasType() {
        String type = "my-type";
        UnsupportedAliasTypeExeception cause = new UnsupportedAliasTypeExeception(type);

        Optional<ApiError> error = handler.apply(cause);

        assertThat(error).isPresent();
        UnsupportedAliasTypeError specificError = (UnsupportedAliasTypeError) error.get();
        assertThat(specificError.getMessage()).isEqualTo(type);
    }

}
