package uk.co.idv.context.usecases.context.expiry;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.context.sequence.Sequences;
import uk.co.idv.context.entities.context.sequence.SequencesMother;

import java.time.Duration;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class ExpiryCalculatorTest {

    private final Duration buffer = Duration.ofSeconds(30);

    private final ExpiryCalculator calculator = new ExpiryCalculator(buffer);

    @Test
    void shouldAddSequencesDurationToCreatedAndThenAddBuffer() {
        Instant created = Instant.now();
        Sequences sequences = SequencesMother.fakeOnly();

        Instant expiry = calculator.calculate(created, sequences);

        Instant expectedExpiry = created
                .plus(sequences.getDuration())
                .plus(buffer);
        assertThat(expiry).isEqualTo(expectedExpiry);
    }

}
