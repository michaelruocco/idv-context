package uk.co.idv.context.usecases.identity.find.data;

import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class DurationCalculatorTest {

    @Test
    void shouldCalculationDurationInMillis() {
        Instant start = Instant.parse("2020-06-06T12:36:14.179Z");
        Instant end = Instant.parse("2020-06-06T12:36:15.179Z");

        long millis = DurationCalculator.millisBetween(start, end);

        assertThat(millis).isEqualTo(1000);
    }

}
