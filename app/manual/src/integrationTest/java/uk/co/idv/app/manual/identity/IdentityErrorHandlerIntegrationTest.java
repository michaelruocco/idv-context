package uk.co.idv.app.manual.identity;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.config.identity.IdentityConfig;
import uk.co.idv.context.adapter.json.error.ApiError;
import uk.co.idv.context.adapter.json.error.handler.ErrorHandler;
import uk.co.idv.context.adapter.json.error.internalserver.InternalServerError;
import uk.co.idv.context.adapter.json.error.updateidvid.CannotUpdateIdvIdError;
import uk.co.idv.context.usecases.identity.save.CannotUpdateIdvIdException;
import uk.co.idv.context.usecases.identity.save.CannotUpdateIdvIdExceptionMother;

import static org.assertj.core.api.Assertions.assertThat;

public class IdentityErrorHandlerIntegrationTest {

    private final IdentityConfig config = IdentityConfig.builder()
            .build();

    private final ErrorHandler handler = config.identityErrorHandler();

    @Test
    void shouldHandleCannotUpdateIdvIdException() {
        CannotUpdateIdvIdException exception = CannotUpdateIdvIdExceptionMother.build();

        ApiError error = handler.apply(exception);

        assertThat(error).isInstanceOf(CannotUpdateIdvIdError.class);
    }

    @Test
    void shouldReturnInternalServerErrorForAnyOtherException() {
        Throwable throwable = new Throwable("error message");

        ApiError error = handler.apply(throwable);

        assertThat(error).isInstanceOf(InternalServerError.class);
        assertThat(error.getMessage()).isEqualTo(throwable.getMessage());
    }

}
