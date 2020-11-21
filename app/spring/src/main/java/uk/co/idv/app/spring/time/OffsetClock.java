package uk.co.idv.app.spring.time;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;

@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class OffsetClock extends Clock {

    private static final Duration DEFAULT_OFFSET = Duration.ZERO;

    private final Clock clock;
    private Duration offset;

    public OffsetClock() {
        this(Clock.systemUTC());
    }

    public OffsetClock(Clock clock) {
        this(clock, DEFAULT_OFFSET);
    }

    @Override
    public ZoneId getZone() {
        return clock.getZone();
    }

    @Override
    public Clock withZone(ZoneId zone) {
        return new OffsetClock(clock.withZone(zone), offset);
    }

    @Override
    public Instant instant() {
        return clock.instant().plus(offset);
    }

    public void setOffset(Duration offset) {
        this.offset = offset;
    }

    public Duration getOffset() {
        return offset;
    }

    public void reset() {
        this.offset = DEFAULT_OFFSET;
    }

}
