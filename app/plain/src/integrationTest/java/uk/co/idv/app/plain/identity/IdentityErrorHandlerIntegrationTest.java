package uk.co.idv.app.plain.identity;

import org.junit.jupiter.api.Test;
import uk.co.idv.identity.config.DefaultIdentityConfig;
import uk.co.idv.identity.config.IdentityConfig;
import uk.co.idv.common.adapter.json.error.ApiError;
import uk.co.idv.common.adapter.json.error.handler.ErrorHandler;
import uk.co.idv.identity.adapter.json.error.updateidvid.CannotUpdateIdvIdError;
import uk.co.idv.identity.entities.identity.CannotUpdateIdvIdException;
import uk.co.idv.identity.entities.identity.CannotUpdateIdvIdExceptionMother;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class IdentityErrorHandlerIntegrationTest {

    private final IdentityConfig config = DefaultIdentityConfig.builder().build();
    private final ErrorHandler handler = config.errorHandler();

    @Test
    void shouldHandleCannotUpdateIdvIdException() {
        CannotUpdateIdvIdException exception = CannotUpdateIdvIdExceptionMother.build();

        Optional<ApiError> error = handler.apply(exception);

        assertThat(error).containsInstanceOf(CannotUpdateIdvIdError.class);
    }

    @Test
    void shouldReturnEmptyIfErrorNotSupported() {
        Throwable throwable = new Throwable("error message");

        Optional<ApiError> error = handler.apply(throwable);

        assertThat(error).isEmpty();
    }

}
