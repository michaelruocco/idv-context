package uk.co.idv.context.entities.policy.method.otp.delivery.eligibility;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.context.eligibility.Eligibility;

import static org.assertj.core.api.Assertions.assertThat;

class UnknownLastUpdatedNotAllowedTest {

    private final Eligibility eligibility = new UnknownLastUpdatedNotAllowed();

    @Test
    void shouldNotBeEligible() {
        assertThat(eligibility.isEligible()).isFalse();
    }

    @Test
    void shouldReturnReason() {
        assertThat(eligibility.getReason()).contains("unknown last updated date is not allowed");
    }

}
