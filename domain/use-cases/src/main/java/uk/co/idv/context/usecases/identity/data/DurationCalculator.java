package uk.co.idv.context.usecases.identity.data;

import java.time.Duration;
import java.time.Instant;

public class DurationCalculator {

    public static long millisBetween(final Instant start, final Instant end) {
        return Duration.between(start, end).toMillis();
    }

}
