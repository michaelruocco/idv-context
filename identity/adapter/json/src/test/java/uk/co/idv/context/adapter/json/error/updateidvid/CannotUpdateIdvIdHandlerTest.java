package uk.co.idv.context.adapter.json.error.updateidvid;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.adapter.json.error.ApiError;
import uk.co.idv.context.adapter.json.error.handler.ErrorHandler;
import uk.co.idv.context.usecases.identity.save.CannotUpdateIdvIdException;
import uk.co.idv.context.usecases.identity.save.CannotUpdateIdvIdExceptionMother;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class CannotUpdateIdvIdHandlerTest {

    private final ErrorHandler handler = new CannotUpdateIdvIdHandler();

    @Test
    void shouldSupportCannotUpdateIdvIdException() {
        CannotUpdateIdvIdException exception =  CannotUpdateIdvIdExceptionMother.build();

        assertThat(handler.apply(exception)).isPresent();
    }

    @Test
    void shouldNotSupportAnyOtherException() {
        Throwable other = new Throwable();

        assertThat(handler.apply(other)).isEmpty();
    }

    @Test
    void shouldReturnCannotUpdateIdvIdError() {
        CannotUpdateIdvIdException cause = CannotUpdateIdvIdExceptionMother.build();

        Optional<ApiError> error = handler.apply(cause);

        assertThat(error).containsInstanceOf(CannotUpdateIdvIdError.class);
    }

    @Test
    void shouldPopulateUpdatedAndExistingIdvIds() {
        CannotUpdateIdvIdException cause = CannotUpdateIdvIdExceptionMother.build();

        Optional<ApiError> error = handler.apply(cause);

        assertThat(error).isPresent();
        CannotUpdateIdvIdError specificError = (CannotUpdateIdvIdError) error.get();
        assertThat(specificError.getUpdated()).isEqualTo(cause.getUpdated());
        assertThat(specificError.getExisting()).isEqualTo(cause.getExisting());
    }

}
