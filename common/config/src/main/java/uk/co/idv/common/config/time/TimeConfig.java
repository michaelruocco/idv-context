package uk.co.idv.common.config.time;

import lombok.RequiredArgsConstructor;

import java.time.Clock;

@RequiredArgsConstructor
public class TimeConfig {

    private final Clock clock;

    public TimeConfig() {
        this(Clock.systemUTC());
    }

    public Clock getClock() {
        return clock;
    }

}
