package uk.co.idv.lockout.entities.policy.hard;

import org.junit.jupiter.api.Test;
import uk.co.idv.lockout.entities.attempt.Attempts;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class HardLockoutStateTest {

    private static final int MAX_NUMBER_OF_ATTEMPTS = 3;

    private final Attempts attempts = mock(Attempts.class);

    private final HardLockoutState state = HardLockoutState.builder()
            .attempts(attempts)
            .maxNumberOfAttempts(MAX_NUMBER_OF_ATTEMPTS)
            .build();

    @Test
    void shouldReturnMaxNumberOfAttempts() {
        assertThat(state.getMaxNumberOfAttempts()).isEqualTo(MAX_NUMBER_OF_ATTEMPTS);
    }

    @Test
    void shouldBeUnlockedIfAttemptsRemaining() {
        int numberOfAttempts = 0;
        givenNumberOfAttempts(numberOfAttempts);

        int expectedNumberOfAttemptsRemaining = expectedNumberOfAttemptsRemaining(numberOfAttempts);
        assertThat(state.isLocked()).isFalse();
        assertThat(state.getNumberOfAttemptsRemaining()).isEqualTo(expectedNumberOfAttemptsRemaining);
        assertThat(state.getMessage()).isEqualTo(toExpectedUnlockedMessage(expectedNumberOfAttemptsRemaining));
    }

    @Test
    void shouldBeLockedIfNoAttemptsRemaining() {
        givenNumberOfAttempts(MAX_NUMBER_OF_ATTEMPTS);

        assertThat(state.isLocked()).isTrue();
        assertThat(state.getNumberOfAttemptsRemaining()).isZero();
        assertThat(state.getMessage()).isEqualTo(toExpectedLockedMessage());
    }

    private void givenNumberOfAttempts(int numberOfAttempts) {
        given(attempts.size()).willReturn(numberOfAttempts);
    }

    private int expectedNumberOfAttemptsRemaining(int numberOfAttempts) {
        return MAX_NUMBER_OF_ATTEMPTS - numberOfAttempts;
    }

    private static String toExpectedLockedMessage() {
        return String.format("maximum number of attempts [%d] reached", MAX_NUMBER_OF_ATTEMPTS);
    }

    private static String toExpectedUnlockedMessage(int numberOfAttemptsRemaining) {
        return String.format("%d attempts remaining", numberOfAttemptsRemaining);
    }

}
