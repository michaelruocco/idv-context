package uk.co.idv.context.entities.lockout.policy.soft;


import java.time.Duration;
import java.time.Instant;

public interface SoftLockMother {

    static SoftLock build() {
        return new SoftLock(
                Duration.ofMinutes(5),
                Instant.parse("2020-07-25T19:45:48.405Z")
        );
    }

}
