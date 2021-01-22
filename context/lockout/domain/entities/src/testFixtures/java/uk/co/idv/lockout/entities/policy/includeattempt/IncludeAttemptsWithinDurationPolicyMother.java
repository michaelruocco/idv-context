package uk.co.idv.lockout.entities.policy.includeattempt;

import uk.co.idv.lockout.entities.ClockMother;

import java.time.Duration;

public interface IncludeAttemptsWithinDurationPolicyMother {

    static IncludeAttemptsWithinDurationPolicy build() {
        return IncludeAttemptsWithinDurationPolicy.builder()
                .clock(ClockMother.build())
                .duration(Duration.ofHours(24))
                .build();
    }

}
