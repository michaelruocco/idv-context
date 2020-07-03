package uk.co.idv.context.adapter.json.error.identitynotfound;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.adapter.json.error.ApiError;
import uk.co.idv.context.adapter.json.error.handler.ErrorHandler;
import uk.co.idv.context.entities.alias.Alias;
import uk.co.idv.context.entities.alias.AliasesMother;
import uk.co.idv.context.entities.alias.IdvIdMother;
import uk.co.idv.context.usecases.identity.find.IdentityNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class IdentityNotFoundHandlerTest {

    private final ErrorHandler handler = new IdentityNotFoundHandler();

    @Test
    void shouldSupportIdentityNotFoundException() {
        IdentityNotFoundException exception = mock(IdentityNotFoundException.class);

        assertThat(handler.supports(exception)).isTrue();
    }

    @Test
    void shouldNotSupportAnyOtherException() {
        Throwable other = new Throwable();

        assertThat(handler.supports(other)).isFalse();
    }

    @Test
    public void shouldReturnIdentityNotFoundError() {
        IdentityNotFoundException exception = new IdentityNotFoundException(AliasesMother.idvIdOnly());

        ApiError error = handler.apply(exception);

        assertThat(error).isInstanceOf(IdentityNotFoundError.class);
    }

    @Test
    public void shouldPopulateMessageWithAliases() {
        Alias alias = IdvIdMother.idvId();
        IdentityNotFoundException exception = new IdentityNotFoundException(AliasesMother.with(alias));

        ApiError error = handler.apply(exception);

        assertThat(error.getMessage()).isEqualTo(alias.format());
    }

}
