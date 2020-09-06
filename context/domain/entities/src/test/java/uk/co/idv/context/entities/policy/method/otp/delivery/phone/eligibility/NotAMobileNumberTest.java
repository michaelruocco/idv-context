package uk.co.idv.context.entities.policy.method.otp.delivery.phone.eligibility;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.context.eligibility.Eligibility;

import static org.assertj.core.api.Assertions.assertThat;

class NotAMobileNumberTest {

    private final Eligibility eligibility = new NotAMobileNumber();

    @Test
    void shouldNotBeEligible() {
        assertThat(eligibility.isEligible()).isFalse();
    }

    @Test
    void shouldReturnReason() {
        assertThat(eligibility.getReason()).contains("not a mobile number");
    }

}
