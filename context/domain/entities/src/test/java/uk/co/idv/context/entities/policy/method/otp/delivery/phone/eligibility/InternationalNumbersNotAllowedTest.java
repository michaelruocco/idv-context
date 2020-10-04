package uk.co.idv.context.entities.policy.method.otp.delivery.phone.eligibility;

import com.neovisionaries.i18n.CountryCode;
import org.junit.jupiter.api.Test;
import uk.co.idv.method.entities.eligibility.Eligibility;

import static org.assertj.core.api.Assertions.assertThat;

class InternationalNumbersNotAllowedTest {

    private final Eligibility eligibility = new InternationalNumbersNotAllowed(CountryCode.GB);

    @Test
    void shouldNotBeEligible() {
        assertThat(eligibility.isEligible()).isFalse();
    }

    @Test
    void shouldReturnReason() {
        assertThat(eligibility.getReason()).contains("international phone numbers not allowed (local country GB)");
    }

}
