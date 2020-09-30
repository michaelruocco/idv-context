package uk.co.idv.context.adapter.dynamo;

import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;

import static org.assertj.core.api.Assertions.assertThat;

class TimeToLiveCalculatorTest {

    private static final Instant NOW = Instant.parse("2020-09-29T01:00:00.000Z");

    private final Clock clock = Clock.fixed(NOW, ZoneId.systemDefault());
    private final Duration timeToLive = Duration.ofMinutes(5);

    private final TimeToLiveCalculator calculator = TimeToLiveCalculator.builder()
            .clock(clock)
            .timeToLive(timeToLive)
            .build();

    @Test
    void shouldCalculateTimeToLiveEpochMillis() {
        long value = calculator.calculate();

        long expectedValue = NOW.toEpochMilli() + timeToLive.toMillis();
        assertThat(value).isEqualTo(expectedValue);
    }

}
