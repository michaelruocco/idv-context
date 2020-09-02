package uk.co.idv.identity.adapter.json.error.updateidvid;

import org.junit.jupiter.api.Test;
import uk.co.idv.identity.adapter.json.error.ApiError;
import uk.co.idv.identity.adapter.json.error.handler.ErrorHandler;
import uk.co.idv.identity.usecases.identity.save.CannotUpdateIdvIdException;
import uk.co.idv.identity.usecases.identity.save.CannotUpdateIdvIdExceptionMother;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class CannotUpdateIdvIdHandlerTest {

    private final ErrorHandler handler = new CannotUpdateIdvIdHandler();

    @Test
    void shouldConvertCannotUpdateIdvIdExceptionToError() {
        CannotUpdateIdvIdException cause = CannotUpdateIdvIdExceptionMother.build();

        Optional<ApiError> error = handler.apply(cause);

        assertThat(error).containsInstanceOf(CannotUpdateIdvIdError.class);
    }

    @Test
    void shouldPopulateUpdatedIdvId() {
        CannotUpdateIdvIdException exception = CannotUpdateIdvIdExceptionMother.build();

        Optional<ApiError> error = handler.apply(exception);

        assertThat(error).map(e -> (CannotUpdateIdvIdError) e)
                .map(CannotUpdateIdvIdError::getUpdated)
                .contains(exception.getUpdated());
    }

    @Test
    void shouldPopulateExistingIdvId() {
        CannotUpdateIdvIdException exception = CannotUpdateIdvIdExceptionMother.build();

        Optional<ApiError> error = handler.apply(exception);

        assertThat(error).map(e -> (CannotUpdateIdvIdError) e)
                .map(CannotUpdateIdvIdError::getExisting)
                .contains(exception.getExisting());
    }

    @Test
    void shouldNotSupportAnyOtherException() {
        Throwable other = new Throwable();

        Optional<ApiError> error = handler.apply(other);

        assertThat(error).isEmpty();
    }

}
