package uk.co.idv.context.adapter.json.error.updateidvid;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.adapter.json.error.ApiError;
import uk.co.idv.context.adapter.json.error.handler.ErrorHandler;
import uk.co.idv.context.usecases.identity.save.CannotUpdateIdvIdException;
import uk.co.idv.context.usecases.identity.save.CannotUpdateIdvIdExceptionMother;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class CannotUpdateIdvIdHandlerTest {

    private final ErrorHandler handler = new CannotUpdateIdvIdHandler();

    @Test
    void shouldSupportCannotUpdateIdvIdException() {
        CannotUpdateIdvIdException exception = mock(CannotUpdateIdvIdException.class);

        assertThat(handler.supports(exception)).isTrue();
    }

    @Test
    void shouldNotSupportAnyOtherException() {
        Throwable other = new Throwable();

        assertThat(handler.supports(other)).isFalse();
    }

    @Test
    void shouldReturnCannotUpdateIdvIdError() {
        CannotUpdateIdvIdException cause = CannotUpdateIdvIdExceptionMother.build();

        ApiError error = handler.apply(cause);

        assertThat(error).isInstanceOf(CannotUpdateIdvIdError.class);
    }

    @Test
    void shouldPopulateUpdatedAndExistingIdvIds() {
        CannotUpdateIdvIdException cause = CannotUpdateIdvIdExceptionMother.build();

        CannotUpdateIdvIdError error = (CannotUpdateIdvIdError) handler.apply(cause);

        assertThat(error.getUpdated()).isEqualTo(cause.getUpdated());
        assertThat(error.getExisting()).isEqualTo(cause.getExisting());
    }

}
