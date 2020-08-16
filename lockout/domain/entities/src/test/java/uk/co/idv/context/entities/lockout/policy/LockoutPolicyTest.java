package uk.co.idv.context.entities.lockout.policy;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.policy.PolicyKey;
import uk.co.idv.context.entities.policy.PolicyRequest;
import uk.co.idv.context.entities.lockout.attempt.Attempts;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class LockoutPolicyTest {

    private final PolicyKey key = mock(PolicyKey.class);
    private final AttemptsFilter attemptsFilter = mock(AttemptsFilter.class);
    private final LockoutStateCalculator stateCalculator = mock(LockoutStateCalculator.class);
    private final RecordAttemptPolicy recordAttemptPolicy = mock(RecordAttemptPolicy.class);

    private final LockoutPolicy policy = LockoutPolicy.builder()
            .key(key)
            .stateCalculator(stateCalculator)
            .recordAttemptPolicy(recordAttemptPolicy)
            .attemptsFilter(attemptsFilter)
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
        LockoutState expectedState = mock(LockoutState.class);
        given(stateCalculator.calculate(request)).willReturn(expectedState);

        LockoutState state = policy.calculateState(request);

        assertThat(state).isEqualTo(expectedState);
    }

    @Test
    void shouldReturnAttemptsWithApplicableAttemptsRemoved() {
        Attempts all = mock(Attempts.class);
        LockoutStateRequest request = givenRequestWithAttempts(all);
        Attempts applicable = mock(Attempts.class);
        given(attemptsFilter.filter(request)).willReturn(applicable);
        Attempts expected = mock(Attempts.class);
        given(all.remove(applicable)).willReturn(expected);

        Attempts attempts = policy.reset(request);

        assertThat(attempts).isEqualTo(expected);
    }

    private LockoutStateRequest givenRequestWithAttempts(Attempts attempts) {
        LockoutStateRequest request = mock(LockoutStateRequest.class);
        given(request.getAttempts()).willReturn(attempts);
        return request;
    }

}
