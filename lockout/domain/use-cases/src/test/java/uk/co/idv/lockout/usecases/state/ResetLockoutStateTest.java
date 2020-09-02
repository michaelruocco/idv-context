package uk.co.idv.lockout.usecases.state;

import org.junit.jupiter.api.Test;
import uk.co.idv.identity.entities.alias.IdvId;
import uk.co.idv.lockout.entities.LockoutRequest;
import uk.co.idv.lockout.entities.LockoutRequestMother;
import uk.co.idv.lockout.entities.attempt.Attempts;
import uk.co.idv.lockout.entities.policy.LockoutPolicy;
import uk.co.idv.lockout.entities.policy.LockoutState;
import uk.co.idv.lockout.entities.policy.ResetResult;
import uk.co.idv.lockout.usecases.attempt.LoadAttempts;
import uk.co.idv.lockout.usecases.attempt.SaveAttempts;
import uk.co.idv.lockout.usecases.policy.LockoutPolicyService;

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
