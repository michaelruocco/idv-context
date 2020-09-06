package uk.co.idv.context.entities.policy.method.otp.delivery.phone.simswap.eligibility;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.context.eligibility.Eligibility;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class SimSwappedAfterCutoffTest {

    private static final Instant SWAPPED_AT = Instant.parse("2020-10-02T07:14:16.267Z");
    private static final Instant CUTOFF = Instant.parse("2020-09-01T06:13:15.266Z");

    private final Eligibility eligibility = new SimSwappedAfterCutoff(SWAPPED_AT, CUTOFF);

    @Test
    void shouldNotBeEligible() {
        assertThat(eligibility.isEligible()).isFalse();
    }

    @Test
    void shouldReturnReason() {
        String expectedMessage = String.format("sim swapped at %s (cutoff %s)", SWAPPED_AT, CUTOFF);
        assertThat(eligibility.getReason()).contains(expectedMessage);
    }

}
