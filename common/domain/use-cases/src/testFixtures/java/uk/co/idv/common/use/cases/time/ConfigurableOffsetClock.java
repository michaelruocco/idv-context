package uk.co.idv.common.use.cases.time;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;

@RequiredArgsConstructor
@AllArgsConstructor
public class ConfigurableOffsetClock extends Clock {

    private final Clock clock ;
    private Duration offset = Duration.ZERO;

    public ConfigurableOffsetClock() {
        this(Clock.systemUTC());
    }

    @Override
    public ZoneId getZone() {
        return clock.getZone();
    }

    @Override
    public Clock withZone(ZoneId zone) {
        return new ConfigurableOffsetClock(clock.withZone(zone), offset);
    }

    @Override
    public Instant instant() {
        return clock.instant().plus(offset);
    }

    public void resetOffset() {
        setOffset(Duration.ZERO);
    }

    public void setOffset(Duration offset) {
        this.offset = offset;
    }

}
