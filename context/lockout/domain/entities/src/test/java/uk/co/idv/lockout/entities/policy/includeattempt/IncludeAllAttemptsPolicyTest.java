package uk.co.idv.lockout.entities.policy.includeattempt;

import org.junit.jupiter.api.Test;
import uk.co.idv.lockout.entities.attempt.Attempts;
import uk.co.idv.lockout.entities.attempt.AttemptsMother;

import static org.assertj.core.api.Assertions.assertThat;

class IncludeAllAttemptsPolicyTest {

    private final IncludeAttemptsPolicy filter = new IncludeAllAttemptsPolicy();

    @Test
    void shouldReturnType() {
        assertThat(filter.getType()).isEqualTo("all-attempts");
    }

    @Test
    void shouldReturnAllAttempts() {
        Attempts attempts = AttemptsMother.build();

        Attempts filtered = filter.apply(attempts);

        assertThat(filtered).isEqualTo(attempts);
    }

}
