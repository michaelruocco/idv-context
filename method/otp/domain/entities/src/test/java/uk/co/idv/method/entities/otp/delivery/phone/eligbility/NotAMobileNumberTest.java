package uk.co.idv.method.entities.otp.delivery.phone.eligbility;

import org.junit.jupiter.api.Test;
import uk.co.idv.method.entities.eligibility.Eligibility;
import uk.co.idv.method.entities.otp.delivery.phone.eligibility.NotAMobileNumber;

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
