package uk.co.idv.context.adapter.json.lockout.error.lockedout;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.adapter.json.error.ApiError;
import uk.co.idv.context.adapter.json.error.handler.ErrorHandler;
import uk.co.idv.context.usecases.lockout.state.LockedOutException;
import uk.co.idv.context.usecases.lockout.state.LockedOutExceptionMother;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class LockedOutHandlerTest {

    private final ErrorHandler handler = new LockedOutHandler();

    @Test
    void shouldConvertLockedOutExceptionToError() {
        LockedOutException exception = LockedOutExceptionMother.build();

        Optional<ApiError> error = handler.apply(exception);

        assertThat(error).containsInstanceOf(LockedOutError.class);
    }

    @Test
    void shouldPopulateErrorMessageWithExceptionMessage() {
        LockedOutException exception = LockedOutExceptionMother.build();

        Optional<ApiError> error = handler.apply(exception);

        String expected = "Identity with idv-id|90b585c6-170f-42a6-ac7c-83d294bdab3f locked";
        assertThat(error).get().hasFieldOrPropertyWithValue("message", expected);
    }

    @Test
    void shouldPopulateMetaWithLockoutState() {
        LockedOutException exception = LockedOutExceptionMother.build();

        Optional<ApiError> error = handler.apply(exception);

        assertThat(error).map(ApiError::getMeta)
                .map(meta -> meta.get("state"))
                .contains(exception.getState());
    }

    @Test
    void shouldNotSupportAnyOtherException() {
        Throwable other = new Throwable();

        Optional<ApiError> error = handler.apply(other);

        assertThat(error).isEmpty();
    }

}
