package uk.co.idv.lockout.entities.policy;

import org.junit.jupiter.api.Test;
import uk.co.idv.lockout.entities.LockoutRequest;
import uk.co.idv.policy.entities.policy.key.PolicyKey;
import uk.co.idv.policy.entities.policy.PolicyRequest;
import uk.co.idv.lockout.entities.attempt.Attempts;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class LockoutPolicyTest {

    private final PolicyKey key = mock(PolicyKey.class);
    private final AttemptsFilter attemptsFilter = mock(AttemptsFilter.class);
    private final LockoutStateCalculator stateCalculator = mock(LockoutStateCalculator.class);
    private final RecordAttemptPolicy recordAttemptPolicy = mock(RecordAttemptPolicy.class);
    private final LockoutRequestConverter converter = mock(LockoutRequestConverter.class);

    private final LockoutPolicy policy = LockoutPolicy.builder()
            .key(key)
            .stateCalculator(stateCalculator)
            .recordAttemptPolicy(recordAttemptPolicy)
            .attemptsFilter(attemptsFilter)
            .converter(converter)
            .build();

    @Test
    void shouldReturnKey() {
        assertThat(policy.getKey()).isEqualTo(key);
    }

    @Test
    void shouldReturnStateCalculator() {
        assertThat(policy.getStateCalculator()).isEqualTo(stateCalculator);
    }

    @Test
    void shouldReturnRecordAttemptPolicy() {
        assertThat(policy.getRecordAttemptPolicy()).isEqualTo(recordAttemptPolicy);
    }

    @Test
    void shouldReturnIdFromKey() {
        UUID expectedId = UUID.randomUUID();
        given(key.getId()).willReturn(expectedId);

        UUID id = policy.getId();

        assertThat(id).isEqualTo(expectedId);
    }

    @Test
    void shouldReturnPriorityFromKey() {
        int expectedPriority = 99;
        given(key.getPriority()).willReturn(expectedPriority);

        int priority = policy.getPriority();

        assertThat(priority).isEqualTo(expectedPriority);
    }

    @Test
    void shouldReturnAppliesToFromKey() {
        PolicyRequest request = mock(PolicyRequest.class);
        given(key.appliesTo(request)).willReturn(true);

        boolean applies = policy.appliesTo(request);

        assertThat(applies).isTrue();
    }

    @Test
    void shouldReturnRecordAttemptFromRecordAttemptPolicy() {
        RecordAttemptRequest request = mock(RecordAttemptRequest.class);
        given(recordAttemptPolicy.shouldRecordAttempt(request)).willReturn(true);

        boolean shouldRecord = policy.shouldRecordAttempt(request);

        assertThat(shouldRecord).isTrue();
    }

    @Test
    void shouldReturnLockoutStateFromStateCalculator() {
        LockoutStateRequest request = mock(LockoutStateRequest.class);
        LockoutState expectedState = givenStateCalculatedFromRequest(request);

        LockoutState state = policy.calculateState(request);

        assertThat(state).isEqualTo(expectedState);
    }

    @Test
    void shouldReturnLockoutStateFromStateCalculatorWhenRequestAndAttemptsProvided() {
        LockoutRequest lockoutRequest = mock(LockoutRequest.class);
        Attempts attempts = mock(Attempts.class);
        LockoutStateRequest stateRequest = givenStateRequestFrom(lockoutRequest, attempts);
        LockoutState expectedState = givenStateCalculatedFromRequest(stateRequest);

        LockoutState state = policy.calculateState(lockoutRequest, attempts);

        assertThat(state).isEqualTo(expectedState);
    }

    @Test
    void shouldReturnStateAttemptsWithApplicableAttemptsRemoved() {
        LockoutStateRequest request = mock(LockoutStateRequest.class);
        Attempts applicable = givenAttemptsApplicableTo(request);
        LockoutStateRequest resetRequest = givenRequestAfterAttemptsRemoved(request, applicable);
        LockoutState expected = givenStateCalculatedFromRequest(resetRequest);

        ResetResult result = policy.resetState(request);

        assertThat(result.getState()).isEqualTo(expected);
        assertThat(result.getAttemptsToRemove()).isEqualTo(applicable);
    }

    @Test
    void shouldReturnAttemptsWithApplicableAttemptsRemovedWhenRequestAndAttemptsProvided() {
        LockoutRequest lockoutRequest = mock(LockoutRequest.class);
        Attempts attempts = mock(Attempts.class);
        LockoutStateRequest stateRequest = givenRequestWithAttempts();
        given(converter.toLockoutStateRequest(lockoutRequest, attempts)).willReturn(stateRequest);
        Attempts applicable = givenAttemptsApplicableTo(stateRequest);
        LockoutStateRequest resetRequest = givenRequestAfterAttemptsRemoved(stateRequest, applicable);
        LockoutState expected = givenStateCalculatedFromRequest(resetRequest);

        ResetResult result = policy.resetState(lockoutRequest, attempts);

        assertThat(result.getState()).isEqualTo(expected);
        assertThat(result.getAttemptsToRemove()).isEqualTo(applicable);
    }

    private LockoutStateRequest givenRequestWithAttempts() {
        return givenRequestWith(mock(Attempts.class));
    }

    private LockoutStateRequest givenRequestWith(Attempts attempts) {
        LockoutStateRequest request = mock(LockoutStateRequest.class);
        given(request.getAttempts()).willReturn(attempts);
        return request;
    }

    private LockoutState givenStateCalculatedFromRequest(LockoutStateRequest request) {
        LockoutState state = mock(LockoutState.class);
        given(stateCalculator.calculate(request)).willReturn(state);
        return state;
    }

    private LockoutStateRequest givenStateRequestFrom(LockoutRequest lockoutRequest, Attempts attempts) {
        LockoutStateRequest stateRequest = mock(LockoutStateRequest.class);
        given(converter.toLockoutStateRequest(lockoutRequest, attempts)).willReturn(stateRequest);
        return stateRequest;
    }

    private Attempts givenAttemptsApplicableTo(LockoutStateRequest stateRequest) {
        Attempts applicable = mock(Attempts.class);
        given(attemptsFilter.filter(stateRequest)).willReturn(applicable);
        return applicable;
    }

    private LockoutStateRequest givenRequestAfterAttemptsRemoved(LockoutStateRequest stateRequest, Attempts attempts) {
        LockoutStateRequest resetRequest = mock(LockoutStateRequest.class);
        given(stateRequest.removeAttempts(attempts)).willReturn(resetRequest);
        return resetRequest;
    }

}
