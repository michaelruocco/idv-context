package uk.co.idv.method.entities.otp.delivery.eligibility;

import org.junit.jupiter.api.Test;
import uk.co.idv.method.entities.eligibility.Eligibility;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class UpdatedAfterCutoffTest {

    private static final Instant UPDATED_AT = Instant.parse("2020-10-02T07:14:16.267Z");
    private static final Instant CUTOFF = Instant.parse("2020-09-01T06:13:15.266Z");

    private final Eligibility eligibility = new UpdatedAfterCutoff(UPDATED_AT, CUTOFF);

    @Test
    void shouldNotBeEligible() {
        assertThat(eligibility.isEligible()).isFalse();
    }

    @Test
    void shouldReturnReason() {
        String expectedMessage = String.format("updated at %s (cutoff %s)", UPDATED_AT, CUTOFF);
        assertThat(eligibility.getReason()).contains(expectedMessage);
    }


}
