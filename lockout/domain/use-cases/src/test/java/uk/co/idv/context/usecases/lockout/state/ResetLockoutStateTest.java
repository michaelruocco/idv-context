package uk.co.idv.context.usecases.lockout.state;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.alias.IdvId;
import uk.co.idv.context.entities.lockout.LockoutRequest;
import uk.co.idv.context.entities.lockout.LockoutRequestMother;
import uk.co.idv.context.entities.lockout.attempt.Attempts;
import uk.co.idv.context.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.context.entities.lockout.policy.LockoutState;
import uk.co.idv.context.entities.lockout.policy.ResetResult;
import uk.co.idv.context.usecases.lockout.attempt.LoadAttempts;
import uk.co.idv.context.usecases.lockout.attempt.SaveAttempts;
import uk.co.idv.context.usecases.lockout.policy.LockoutPolicyService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class ResetLockoutStateTest {

    private final LockoutPolicyService policyService = mock(LockoutPolicyService.class);
    private final LoadAttempts loadAttempts = mock(LoadAttempts.class);
    private final SaveAttempts saveAttempts = mock(SaveAttempts.class);

    private final ResetLockoutState resetState = ResetLockoutState.builder()
            .policyService(policyService)
            .loadAttempts(loadAttempts)
            .saveAttempts(saveAttempts)
            .build();

    @Test
    void shouldLoadLockoutPolicyAndAttemptsToResetLockoutState() {
        LockoutRequest request = LockoutRequestMother.build();
        LockoutPolicy policy = givenPolicyLoadedForRequest(request);
        Attempts attempts = givenAttemptsLoadedForIdvId(request.getIdvId());
        LockoutState expectedState = mock(LockoutState.class);
        given(policy.calculateState(request, attempts)).willReturn(expectedState);
        ResetResult resetResult = mock(ResetResult.class);
        Attempts resetAttempts = mock(Attempts.class);
        given(resetResult.getState()).willReturn(expectedState);
        given(resetResult.getAttemptsToRemove()).willReturn(resetAttempts);
        Attempts remainingAttempts = givenRemainingAttempts(attempts, resetAttempts);
        given(policy.resetState(request, attempts)).willReturn(resetResult);

        LockoutState state = resetState.reset(request);

        assertThat(state).isEqualTo(expectedState);
        verify(saveAttempts).save(remainingAttempts);
    }

    private LockoutPolicy givenPolicyLoadedForRequest(LockoutRequest request) {
        LockoutPolicy policy = mock(LockoutPolicy.class);
        given(policyService.loadHighestPriority(request)).willReturn(policy);
        return policy;
    }

    private Attempts givenAttemptsLoadedForIdvId(IdvId idvId) {
        Attempts attempts = mock(Attempts.class);
        given(loadAttempts.load(idvId)).willReturn(attempts);
        return attempts;
    }

    private Attempts givenRemainingAttempts(Attempts original, Attempts reset) {
        Attempts remainingAttempts = mock(Attempts.class);
        given(original.remove(reset)).willReturn(remainingAttempts);
        return remainingAttempts;
    }

}
