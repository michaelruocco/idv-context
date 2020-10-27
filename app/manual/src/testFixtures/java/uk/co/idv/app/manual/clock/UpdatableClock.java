package uk.co.idv.app.manual.clock;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;

@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UpdatableClock extends Clock {

    private Instant now;

    @Override
    public ZoneId getZone() {
        return ZoneId.systemDefault();
    }

    @Override
    public Clock withZone(ZoneId zone) {
        return this;
    }

    @Override
    public Instant instant() {
        return now;
    }

    public void plus(Duration duration) {
        now = now.plus(duration);
    }

    public void minus(Duration duration) {
        now = now.minus(duration);
    }

    public void setNow(Instant now) {
        this.now = now;
    }

}
