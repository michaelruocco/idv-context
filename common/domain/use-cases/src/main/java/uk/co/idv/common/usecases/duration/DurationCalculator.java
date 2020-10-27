package uk.co.idv.common.usecases.duration;

import java.time.Duration;
import java.time.Instant;

public interface DurationCalculator {

    static long millisBetweenNowAnd(Instant start) {
        return Duration.between(start, Instant.now()).toMillis();
    }

}
