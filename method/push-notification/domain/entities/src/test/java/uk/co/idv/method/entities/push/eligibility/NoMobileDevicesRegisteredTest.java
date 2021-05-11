package uk.co.idv.method.entities.push.eligibility;

import org.junit.jupiter.api.Test;
import uk.co.idv.method.entities.eligibility.Eligibility;

import static org.assertj.core.api.Assertions.assertThat;

class NoMobileDevicesRegisteredTest {

    private final Eligibility eligibility = new NoMobileDevicesRegistered();

    @Test
    void shouldNotBeEligible() {
        assertThat(eligibility.isEligible()).isFalse();
    }

    @Test
    void shouldReturnReason() {
        assertThat(eligibility.getReason()).contains("No mobile devices registered");
    }

}
