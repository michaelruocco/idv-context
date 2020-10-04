package uk.co.idv.context.entities.context.method.otp.delivery.eligibility;

import org.junit.jupiter.api.Test;
import uk.co.idv.method.entities.eligibility.Eligibility;

import static org.assertj.core.api.Assertions.assertThat;

class NoEligibleDeliveryMethodsAvailableTest {

    private final Eligibility eligibility = new NoEligibleDeliveryMethodsAvailable();

    @Test
    void shouldNotBeEligible() {
        assertThat(eligibility.isEligible()).isFalse();
    }

    @Test
    void shouldReturnReason() {
        assertThat(eligibility.getReason()).contains("No eligible delivery methods available");
    }

}
