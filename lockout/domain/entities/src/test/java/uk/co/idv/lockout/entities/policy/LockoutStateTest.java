package uk.co.idv.lockout.entities.policy;

import org.junit.jupiter.api.Test;
import uk.co.idv.lockout.entities.attempt.Attempt;
import uk.co.idv.lockout.entities.attempt.Attempts;

import java.util.Collection;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class LockoutStateTest {

    private final Attempts attempts = mock(Attempts.class);

    private final LockoutState state = new FakeLockoutState(attempts);

    @Test
    void shouldReturnNumberOfAttempts() {
        int numberOfAttempts = 2;
        givenNumberOfAttempts(numberOfAttempts);

        assertThat(state.getNumberOfAttempts()).isEqualTo(numberOfAttempts);
    }

    @Test
    void shouldReturnAttemptsAsCollectionFromAttempts() {
        Collection<Attempt> expected = Collections.emptyList();
        given(attempts.toCollection()).willReturn(expected);

        Collection<Attempt> actual = state.attemptsCollection();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void shouldBeUnlocked() {
        assertThat(state.isLocked()).isFalse();
    }

    @Test
    void shouldReturnMessage() {
        assertThat(state.getMessage()).isEqualTo("fake message");
    }

    private void givenNumberOfAttempts(int numberOfAttempts) {
        given(attempts.size()).willReturn(numberOfAttempts);
    }

    private static class FakeLockoutState extends LockoutState {

        public FakeLockoutState(Attempts attempts) {
            super(attempts);
        }

        @Override
        public boolean isLocked() {
            return false;
        }

        @Override
        public String getMessage() {
            return "fake message";
        }

    }

}
