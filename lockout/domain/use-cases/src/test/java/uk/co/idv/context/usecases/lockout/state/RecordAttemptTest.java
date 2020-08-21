package uk.co.idv.context.usecases.lockout.state;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.lockout.attempt.Attempt;
import uk.co.idv.context.entities.lockout.attempt.AttemptMother;
import uk.co.idv.context.entities.lockout.attempt.Attempts;
import uk.co.idv.context.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.context.entities.lockout.policy.LockoutState;
import uk.co.idv.context.entities.lockout.policy.RecordAttemptRequest;
import uk.co.idv.context.usecases.lockout.attempt.SaveAttempts;
import uk.co.idv.context.usecases.lockout.policy.LockoutPolicyService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class RecordAttemptTest {

    private final LockoutPolicyService policyService = mock(LockoutPolicyService.class);
    private final ResetLockoutState reset = mock(ResetLockoutState.class);
    private final SaveAttempts save = mock(SaveAttempts.class);
    private final LoadLockoutState load = mock(LoadLockoutState.class);

    private final RecordAttempt recordAttempt = RecordAttempt.builder()
            .policyService(policyService)
            .reset(reset)
            .save(save)
            .load(load)
            .build();

    @Test
    void shouldLoadLockoutStateIfPolicyShouldNotRecordAttempt() {
        RecordAttemptRequest request = mock(RecordAttemptRequest.class);
        LockoutPolicy policy = givenPolicyReturnedForRequest(request);
        given(policy.shouldRecordAttempt(request)).willReturn(false);
        LockoutState expectedState = mock(LockoutState.class);
        given(load.load(request, policy)).willReturn(expectedState);

        LockoutState state = recordAttempt.recordIfRequired(request);

        assertThat(state).isEqualTo(expectedState);
    }

    @Test
    void shouldResetLockoutStateIfPolicyShouldRecordAttemptAndAttemptIsSuccessful() {
        RecordAttemptRequest request = givenRequestWithAttempt(AttemptMother.successful());
        LockoutPolicy policy = givenPolicyReturnedForRequest(request);
        given(policy.shouldRecordAttempt(request)).willReturn(true);
        LockoutState expectedState = mock(LockoutState.class);
        given(reset.reset(request, policy)).willReturn(expectedState);

        LockoutState state = recordAttempt.recordIfRequired(request);

        assertThat(state).isEqualTo(expectedState);
    }

    @Test
    void shouldThrowExceptionIfLockoutStateIsLocked() {
        Attempt attempt = AttemptMother.unsuccessful();
        RecordAttemptRequest request = givenRequestWithAttempt(attempt);
        LockoutPolicy policy = givenPolicyReturnedForRequest(request);
        given(policy.shouldRecordAttempt(request)).willReturn(true);
        LockoutState state = mock(LockoutState.class);
        given(load.load(request, policy)).willReturn(state);
        given(state.isLocked()).willReturn(true);

        LockedOutException error = catchThrowableOfType(
                () -> recordAttempt.recordIfRequired(request),
                LockedOutException.class
        );

        assertThat(error.getState()).isEqualTo(state);
    }

    @Test
    void shouldSaveFailedAttemptThenCalculateLockoutStateIfPolicyShouldRecordAttemptAndAttemptIsUnsuccessful() {
        Attempt attempt = AttemptMother.unsuccessful();
        RecordAttemptRequest request = givenRequestWithAttempt(attempt);
        LockoutPolicy policy = givenPolicyReturnedForRequest(request);
        given(policy.shouldRecordAttempt(request)).willReturn(true);
        Attempts loadedAttempts = mock(Attempts.class);
        LockoutState loadedState = givenLockoutStateWithAttempts(loadedAttempts);
        given(load.load(request, policy)).willReturn(loadedState);
        Attempts updatedAttempts = mock(Attempts.class);
        given(save.save(attempt, loadedAttempts)).willReturn(updatedAttempts);
        LockoutState expectedState = mock(LockoutState.class);
        given(policy.calculateState(request, updatedAttempts)).willReturn(expectedState);

        LockoutState state = recordAttempt.recordIfRequired(request);

        assertThat(state).isEqualTo(expectedState);
    }

    private LockoutPolicy givenPolicyReturnedForRequest(RecordAttemptRequest request) {
        LockoutPolicy policy = mock(LockoutPolicy.class);
        given(policyService.loadHighestPriority(request)).willReturn(policy);
        return policy;
    }

    private RecordAttemptRequest givenRequestWithAttempt(Attempt attempt) {
        RecordAttemptRequest request = mock(RecordAttemptRequest.class);
        given(request.getAttempt()).willReturn(attempt);
        return request;
    }

    private LockoutState givenLockoutStateWithAttempts(Attempts attempts) {
        LockoutState state = mock(LockoutState.class);
        given(state.getAttempts()).willReturn(attempts);
        return state;
    }

}
