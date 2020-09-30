package uk.co.idv.context.adapter.dynamo;

import lombok.Builder;

import java.time.Clock;
import java.time.Duration;

@Builder
public class TimeToLiveCalculator {

    private final Duration timeToLive;
    private final Clock clock;

    public long calculate() {
        return clock.instant().toEpochMilli() + timeToLive.toMillis();
    }

}
