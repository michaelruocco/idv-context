package uk.co.idv.context.adapter.json.error.identitynotfound;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.adapter.json.error.ApiError;
import uk.co.idv.context.adapter.json.error.handler.ErrorHandler;
import uk.co.idv.context.entities.alias.Alias;
import uk.co.idv.context.entities.alias.AliasesMother;
import uk.co.idv.context.entities.alias.IdvIdMother;
import uk.co.idv.context.usecases.identity.find.IdentityNotFoundException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class IdentityNotFoundHandlerTest {

    private final ErrorHandler handler = new IdentityNotFoundHandler();

    @Test
    void shouldSupportIdentityNotFoundException() {
        IdentityNotFoundException exception = new IdentityNotFoundException(AliasesMother.idvIdOnly());

        assertThat(handler.apply(exception)).isPresent();
    }

    @Test
    void shouldNotSupportAnyOtherException() {
        Throwable other = new Throwable();

        assertThat(handler.apply(other)).isEmpty();
    }

    @Test
    void shouldReturnIdentityNotFoundError() {
        IdentityNotFoundException exception = new IdentityNotFoundException(AliasesMother.idvIdOnly());

        Optional<ApiError> error = handler.apply(exception);

        assertThat(error).containsInstanceOf(IdentityNotFoundError.class);
    }

    @Test
    void shouldPopulateMessageWithAliases() {
        Alias alias = IdvIdMother.idvId();
        IdentityNotFoundException exception = new IdentityNotFoundException(AliasesMother.with(alias));

        Optional<ApiError> error = handler.apply(exception);

        assertThat(error).isPresent();
        assertThat(error.get().getMessage()).isEqualTo(alias.format());
    }

}
