package uk.co.idv.context.adapter.stub.identity.data;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class DelayTest {

    @Test
    void shouldDelayForSpecifiedTime() {
        long millis = 5;
        Delay delay = new Delay(millis);

        Instant start = Instant.now();
        delay.execute();
        Instant end = Instant.now();

        assertThat(Duration.between(start, end).toMillis()).isGreaterThanOrEqualTo(millis);
    }

    @Test
    void shouldReturnDuration() {
        long millis = 5;

        Delay delay = new Delay(millis);

        assertThat(delay.getDuration().toMillis()).isEqualTo(millis);
    }

}
