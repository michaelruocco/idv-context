package uk.co.idv.lockout.entities.policy.soft;

import org.junit.jupiter.api.Test;
import uk.co.idv.lockout.entities.attempt.Attempts;
import uk.co.idv.lockout.entities.attempt.AttemptsMother;
import uk.co.idv.lockout.entities.policy.AllAttemptsFilter;

import static org.assertj.core.api.Assertions.assertThat;

class AllAttemptsFilterTest {

    private final AttemptsFilter filter = new AllAttemptsFilter();

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
