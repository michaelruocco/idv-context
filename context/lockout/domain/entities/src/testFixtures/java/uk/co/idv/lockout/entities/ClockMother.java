package uk.co.idv.lockout.entities;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

public interface ClockMother {

    static Clock build() {
        return Clock.fixed(Instant.parse("2021-01-21T07:54:50Z"), ZoneId.systemDefault());
    }

}
