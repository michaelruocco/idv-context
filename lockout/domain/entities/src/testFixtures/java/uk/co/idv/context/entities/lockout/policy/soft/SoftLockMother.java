package uk.co.idv.context.entities.lockout.policy.soft;


import uk.co.idv.context.entities.lockout.policy.soft.SoftLock.SoftLockBuilder;

import java.time.Duration;
import java.time.Instant;

public interface SoftLockMother {

    static SoftLock build() {
        return builder().build();
    }

    static SoftLock startingAt(Instant start) {
        return builder()
                .start(start)
                .build();
    }

    static SoftLockBuilder builder() {
        return SoftLock.builder()
                .start(Instant.parse("2020-07-25T19:45:48.405Z"))
                .duration(Duration.ofMinutes(5));
    }

}
