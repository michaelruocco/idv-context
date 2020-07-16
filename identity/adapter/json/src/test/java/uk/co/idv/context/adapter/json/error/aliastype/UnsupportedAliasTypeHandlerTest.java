package uk.co.idv.context.adapter.json.error.aliastype;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.adapter.json.error.ApiError;
import uk.co.idv.context.adapter.json.error.handler.ErrorHandler;
import uk.co.idv.context.entities.alias.UnsupportedAliasTypeExeception;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class UnsupportedAliasTypeHandlerTest {

    private final ErrorHandler handler = new UnsupportedAliasTypeHandler();

    @Test
    void shouldSupportCannotUpdateIdvIdException() {
        UnsupportedAliasTypeExeception exception = mock(UnsupportedAliasTypeExeception.class);

        assertThat(handler.supports(exception)).isTrue();
    }

    @Test
    void shouldNotSupportAnyOtherException() {
        Throwable other = new Throwable();

        assertThat(handler.supports(other)).isFalse();
    }

    @Test
    void shouldReturnUnsupportedAliasTypeError() {
        UnsupportedAliasTypeExeception cause = mock(UnsupportedAliasTypeExeception.class);

        ApiError error = handler.apply(cause);

        assertThat(error).isInstanceOf(UnsupportedAliasTypeError.class);
    }

    @Test
    void shouldPopulateMessageWithAliasType() {
        String type = "my-type";
        UnsupportedAliasTypeExeception cause = new UnsupportedAliasTypeExeception(type);

        UnsupportedAliasTypeError error = (UnsupportedAliasTypeError) handler.apply(cause);

        assertThat(error.getMessage()).isEqualTo(type);
    }

}
