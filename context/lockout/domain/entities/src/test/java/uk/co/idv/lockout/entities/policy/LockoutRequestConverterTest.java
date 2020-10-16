package uk.co.idv.lockout.entities.policy;

import org.junit.jupiter.api.Test;
import uk.co.idv.lockout.entities.LockoutRequest;
import uk.co.idv.lockout.entities.LockoutRequestMother;
import uk.co.idv.lockout.entities.attempt.Attempts;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class LockoutRequestConverterTest {

    private final LockoutRequestConverter converter = new LockoutRequestConverter();

    @Test
    void shouldPopulateAliases() {
        LockoutRequest lockoutRequest = LockoutRequestMother.build();

        LockoutStateRequest stateRequest = converter.toLockoutStateRequest(lockoutRequest, mock(Attempts.class));

        assertThat(stateRequest.getAliases()).isEqualTo(lockoutRequest.getAliases());
    }

    @Test
    void shouldPopulateTimestamp() {
        LockoutRequest lockoutRequest = LockoutRequestMother.build();

        LockoutStateRequest stateRequest = converter.toLockoutStateRequest(lockoutRequest, mock(Attempts.class));

        assertThat(stateRequest.getTimestamp()).isEqualTo(lockoutRequest.getTimestamp());
    }

    @Test
    void shouldPopulateAttempts() {
        Attempts attempts = mock(Attempts.class);

        LockoutStateRequest stateRequest = converter.toLockoutStateRequest(mock(LockoutRequest.class), attempts);

        assertThat(stateRequest.getAttempts()).isEqualTo(attempts);
    }

}
