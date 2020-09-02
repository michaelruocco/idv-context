package uk.co.idv.app.manual.identity;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.config.identity.IdentityConfig;
import uk.co.idv.context.adapter.json.error.ApiError;
import uk.co.idv.context.adapter.json.error.handler.ErrorHandler;
import uk.co.idv.context.adapter.json.error.updateidvid.CannotUpdateIdvIdError;
import uk.co.idv.identity.usecases.identity.save.CannotUpdateIdvIdException;
import uk.co.idv.identity.usecases.identity.save.CannotUpdateIdvIdExceptionMother;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class IdentityErrorHandlerIntegrationTest {

    private final IdentityConfig config = IdentityConfig.builder()
            .build();

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
