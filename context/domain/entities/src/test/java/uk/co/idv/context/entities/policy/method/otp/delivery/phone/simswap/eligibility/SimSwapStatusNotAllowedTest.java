package uk.co.idv.context.entities.policy.method.otp.delivery.phone.simswap.eligibility;

import org.junit.jupiter.api.Test;
import uk.co.idv.method.entities.eligibility.Eligibility;

import static org.assertj.core.api.Assertions.assertThat;

class SimSwapStatusNotAllowedTest {

    private static final String STATUS = "my-status";

    private final Eligibility eligibility = new SimSwapStatusNotAllowed(STATUS);

    @Test
    void shouldNotBeEligible() {
        assertThat(eligibility.isEligible()).isFalse();
    }

    @Test
    void shouldReturnReason() {
        assertThat(eligibility.getReason()).contains("sim swap status my-status is not allowed");
    }

}
