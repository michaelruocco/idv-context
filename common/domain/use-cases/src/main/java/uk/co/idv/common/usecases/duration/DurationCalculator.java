package uk.co.idv.common.usecases.duration;

import java.time.Duration;
import java.time.Instant;

public class DurationCalculator {

    private DurationCalculator() {
        // utility class
    }

    public static long millisBetweenNowAnd(Instant start) {
        return Duration.between(start, Instant.now()).toMillis();
    }

}
