package uk.co.idv.context.usecases.lockout;

import org.junit.jupiter.api.Test;
import uk.co.idv.identity.entities.alias.IdvIdMother;
import uk.co.idv.context.entities.lockout.LockoutRequest;
import uk.co.idv.context.entities.lockout.LockoutRequestMother;
import uk.co.idv.context.entities.lockout.policy.LockoutState;
import uk.co.idv.context.entities.lockout.policy.RecordAttemptRequest;
import uk.co.idv.context.usecases.lockout.state.LoadLockoutState;
import uk.co.idv.context.usecases.lockout.state.LockedOutException;
import uk.co.idv.context.usecases.lockout.state.RecordAttempt;
import uk.co.idv.context.usecases.lockout.state.ResetLockoutState;
import uk.co.idv.context.usecases.lockout.state.ValidateLockoutState;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

class LockoutServiceTest {

    private final RecordAttempt recordAttempt = mock(RecordAttempt.class);
    private final LoadLockoutState load = mock(LoadLockoutState.class);
    private final ValidateLockoutState validate = mock(ValidateLockoutState.class);
    private final ResetLockoutState reset = mock(ResetLockoutState.class);

    private final LockoutService service = LockoutService.builder()
            .recordAttempt(recordAttempt)
            .load(load)
            .validate(validate)
            .reset(reset)
            .build();

    @Test
    void shouldLoadLockoutState() {
        LockoutRequest request = LockoutRequestMother.build();
        LockoutState expectedState = givenStateLoadedFor(request);

        LockoutState state = service.loadState(request);

        assertThat(state).isEqualTo(expectedState);
    }

    @Test
    void shouldResetLockoutState() {
        LockoutRequest request = LockoutRequestMother.build();
        LockoutState expectedState = givenStateResetFor(request);

        LockoutState state = service.resetState(request);

        assertThat(state).isEqualTo(expectedState);
    }

    @Test
    void shouldRecordAttemptState() {
        RecordAttemptRequest request = mock(RecordAttemptRequest.class);
        LockoutState expectedState = givenStateReturnedAfterRecording(request);

        LockoutState state = service.recordAttemptIfRequired(request);

        assertThat(state).isEqualTo(expectedState);
    }

    @Test
    void shouldReturnStateIfStateIsValid() {
        LockoutRequest request = LockoutRequestMother.build();
        LockoutState expectedState = givenStateLoadedFor(request);

        LockoutState state = service.loadAndValidateState(request);

        assertThat(state).isEqualTo(expectedState);
    }

    @Test
    void shouldThrowExceptionIfStateIsLocked() {
        LockoutRequest request = LockoutRequestMother.build();
        LockoutState state = givenStateLoadedFor(request);
        Throwable expectedError = givenExceptionThrownWhenValidatingState(state);

        Throwable error = catchThrowable(() -> service.loadAndValidateState(request));

        assertThat(error).isEqualTo(expectedError);
    }

    private LockoutState givenStateLoadedFor(LockoutRequest request) {
        LockoutState state = mock(LockoutState.class);
        given(load.load(request)).willReturn(state);
        given(state.getIdvId()).willReturn(IdvIdMother.idvId());
        return state;
    }

    private LockoutState givenStateResetFor(LockoutRequest request) {
        LockoutState state = mock(LockoutState.class);
        given(reset.reset(request)).willReturn(state);
        return state;
    }

    private LockoutState givenStateReturnedAfterRecording(RecordAttemptRequest request) {
        LockoutState state = mock(LockoutState.class);
        given(recordAttempt.recordIfRequired(request)).willReturn(state);
        return state;
    }

    private Throwable givenExceptionThrownWhenValidatingState(LockoutState state) {
        Throwable throwable = new LockedOutException(state);
        doThrow(throwable).when(validate).validate(state);
        return throwable;
    }

}
