package uk.co.idv.context.usecases.context.expiry;

import lombok.RequiredArgsConstructor;

import java.time.Duration;
import java.time.Instant;

@RequiredArgsConstructor
public class ExpiryCalculator {

    private static final Duration DEFAULT_BUFFER = Duration.ofMinutes(1);

    private final Duration buffer;

    public ExpiryCalculator() {
        this(DEFAULT_BUFFER);
    }

    public Instant calculate(Instant created, Duration duration) {
        return created.plus(duration).plus(buffer);
    }

}
