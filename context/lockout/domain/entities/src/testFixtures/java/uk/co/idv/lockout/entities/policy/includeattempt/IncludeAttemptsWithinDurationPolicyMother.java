package uk.co.idv.lockout.entities.policy.includeattempt;

import uk.co.idv.lockout.entities.ClockMother;
import uk.co.idv.lockout.entities.policy.includeattempt.IncludeAttemptsWithinDurationPolicy.IncludeAttemptsWithinDurationPolicyBuilder;

import java.time.Clock;
import java.time.Duration;

public interface IncludeAttemptsWithinDurationPolicyMother {

    static IncludeAttemptsWithinDurationPolicy withClock(Clock clock) {
        return builder().clock(clock).build();
    }

    static IncludeAttemptsWithinDurationPolicy build() {
        return builder().build();
    }

    static IncludeAttemptsWithinDurationPolicyBuilder builder() {
        return IncludeAttemptsWithinDurationPolicy.builder()
                .clock(ClockMother.build())
                .duration(Duration.ofHours(24));
    }

}
