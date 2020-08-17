package uk.co.idv.context.entities.lockout.policy;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.lockout.LockoutRequest;
import uk.co.idv.context.entities.lockout.LockoutRequestMother;
import uk.co.idv.context.entities.lockout.attempt.Attempts;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class LockoutRequestConverterTest {

    private final LockoutRequestConverter converter = new LockoutRequestConverter();

    @Test
    void shouldPopulateAlias() {
        LockoutRequest lockoutRequest = LockoutRequestMother.build();

        LockoutStateRequest stateRequest = converter.toLockoutStateRequest(lockoutRequest, mock(Attempts.class));

        assertThat(stateRequest.getAlias()).isEqualTo(lockoutRequest.getAlias());
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
