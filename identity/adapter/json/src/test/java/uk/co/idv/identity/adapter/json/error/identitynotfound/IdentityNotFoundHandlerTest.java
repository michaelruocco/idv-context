package uk.co.idv.identity.adapter.json.error.identitynotfound;

import org.junit.jupiter.api.Test;
import uk.co.idv.identity.adapter.json.error.ApiError;
import uk.co.idv.identity.adapter.json.error.handler.ErrorHandler;
import uk.co.idv.identity.usecases.identity.find.IdentityNotFoundException;
import uk.co.idv.identity.usecases.identity.find.IdentityNotFoundExceptionMother;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class IdentityNotFoundHandlerTest {

    private final ErrorHandler handler = new IdentityNotFoundHandler();

    @Test
    void shouldConvertIdentityNotFoundExceptionToError() {
        IdentityNotFoundException exception = IdentityNotFoundExceptionMother.build();

        Optional<ApiError> error = handler.apply(exception);

        assertThat(error).containsInstanceOf(IdentityNotFoundError.class);
    }

    @Test
    void shouldPopulateErrorMessageWithExceptionMessage() {
        IdentityNotFoundException exception = IdentityNotFoundExceptionMother.build();

        Optional<ApiError> error = handler.apply(exception);

        assertThat(error)
                .map(ApiError::getMessage)
                .contains(exception.getMessage());
    }

    @Test
    void shouldNotSupportAnyOtherException() {
        Throwable other = new Throwable();

        Optional<ApiError> error = handler.apply(other);

        assertThat(error).isEmpty();
    }

}
