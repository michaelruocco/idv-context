package uk.co.idv.context.lockout.policy.soft;

import java.time.Duration;
import java.time.Instant;

public interface SoftLockMother {

    static SoftLock build() {
        return SoftLock.builder()
                .duration(Duration.ofMinutes(5))
                .expiry(Instant.parse("2020-07-25T19:45:48.405Z"))
                .build();
    }

}
