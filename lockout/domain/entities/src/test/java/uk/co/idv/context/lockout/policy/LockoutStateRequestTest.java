package uk.co.idv.context.lockout.policy;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.lockout.attempt.VerificationAttempt;
import uk.co.idv.context.lockout.attempt.VerificationAttemptMother;
import uk.co.idv.context.lockout.attempt.VerificationAttempts;
import uk.co.idv.context.lockout.attempt.VerificationAttemptsMother;

import static org.assertj.core.api.Assertions.assertThat;

class LockoutStateRequestTest {

    @Test
    void shouldReturnAttempt() {
        VerificationAttempt attempt = VerificationAttemptMother.build();

        LockoutStateRequest request = LockoutStateRequest.builder()
                .attempt(attempt)
                .build();

        assertThat(request.getAttempt()).isEqualTo(attempt);
    }

    @Test
    void shouldReturnAttempts() {
        VerificationAttempts attempts = VerificationAttemptsMother.build();

        LockoutStateRequest request = LockoutStateRequest.builder()
                .attempts(attempts)
                .build();

        assertThat(request.getAttempts()).isEqualTo(attempts);
    }

    @Test
    void shouldReturnAliasFromAttempt() {
        VerificationAttempt attempt = VerificationAttemptMother.build();

        LockoutStateRequest request = LockoutStateRequest.builder()
                .attempt(attempt)
                .build();

        assertThat(request.getAlias()).isEqualTo(attempt.getAlias());
    }

}
