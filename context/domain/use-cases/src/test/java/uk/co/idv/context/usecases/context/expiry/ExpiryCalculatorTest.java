package uk.co.idv.context.usecases.context.expiry;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class ExpiryCalculatorTest {

    private final Duration buffer = Duration.ofSeconds(30);

    private final ExpiryCalculator calculator = new ExpiryCalculator(buffer);

    @Test
    void shouldAddSequencesDurationToCreatedAndThenAddBuffer() {
        Instant created = Instant.now();
        Duration duration = Duration.ofMinutes(5);

        Instant expiry = calculator.calculate(created, duration);

        Instant expectedExpiry = created
                .plus(duration)
                .plus(buffer);
        assertThat(expiry).isEqualTo(expectedExpiry);
    }

}
