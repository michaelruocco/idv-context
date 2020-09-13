package uk.co.idv.context.entities.policy.method.otp.delivery.phone;

import com.neovisionaries.i18n.CountryCode;
import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.context.eligibility.Eligibility;
import uk.co.idv.context.entities.policy.method.otp.delivery.LastUpdatedConfig;
import uk.co.idv.context.entities.policy.method.otp.delivery.phone.eligibility.InternationalNumbersNotAllowed;
import uk.co.idv.context.entities.policy.method.otp.delivery.phone.simswap.SimSwapConfig;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class OtpPhoneNumberConfigTest {

    @Test
    void shouldReturnCountry() {
        CountryCode country = CountryCode.GB;

        OtpPhoneNumberConfig config = OtpPhoneNumberConfig.builder()
                .country(country)
                .build();

        assertThat(config.getCountry()).isEqualTo(country);
    }

    @Test
    void shouldReturnAllowInternational() {
        OtpPhoneNumberConfig config = OtpPhoneNumberConfig.builder()
                .allowInternational(true)
                .build();

        assertThat(config.isAllowInternational()).isTrue();
    }

    @Test
    void shouldReturnLastUpdatedConfig() {
        LastUpdatedConfig lastUpdatedConfig = mock(LastUpdatedConfig.class);

        OtpPhoneNumberConfig config = OtpPhoneNumberConfig.builder()
                .lastUpdatedConfig(lastUpdatedConfig)
                .build();

        assertThat(config.getLastUpdatedConfig()).isEqualTo(lastUpdatedConfig);
    }

    @Test
    void shouldReturnSimSwapConfig() {
        SimSwapConfig simSwapConfig = mock(SimSwapConfig.class);

        OtpPhoneNumberConfig config = OtpPhoneNumberConfig.builder()
                .simSwapConfig(simSwapConfig)
                .build();

        assertThat(config.getSimSwapConfig()).contains(simSwapConfig);
    }

    @Test
    void shouldReturnLastUpdatedEligibilityIfInternationalAllowedAndNumberIsInternational() {
        OtpPhoneNumber number = givenInternationalPhoneNumber();
        Instant now = Instant.now();
        LastUpdatedConfig lastUpdatedConfig = mock(LastUpdatedConfig.class);
        Eligibility expectedEligibility = mock(Eligibility.class);
        given(lastUpdatedConfig.toEligibility(number, now)).willReturn(expectedEligibility);
        OtpPhoneNumberConfig config = OtpPhoneNumberConfig.builder()
                .allowInternational(true)
                .lastUpdatedConfig(lastUpdatedConfig)
                .build();

        Eligibility eligibility = config.toEligibility(number, now);

        assertThat(eligibility).isEqualTo(expectedEligibility);
    }

    @Test
    void shouldReturnLastUpdatedEligibilityIfInternationalAllowedAndNumberIsLocal() {
        OtpPhoneNumber number = givenLocalPhoneNumber();
        Instant now = Instant.now();
        LastUpdatedConfig lastUpdatedConfig = mock(LastUpdatedConfig.class);
        Eligibility expectedEligibility = mock(Eligibility.class);
        given(lastUpdatedConfig.toEligibility(number, now)).willReturn(expectedEligibility);
        OtpPhoneNumberConfig config = OtpPhoneNumberConfig.builder()
                .allowInternational(true)
                .lastUpdatedConfig(lastUpdatedConfig)
                .build();

        Eligibility eligibility = config.toEligibility(number, now);

        assertThat(eligibility).isEqualTo(expectedEligibility);
    }

    @Test
    void shouldReturnLastUpdatedEligibilityIfInternationalNotAllowedAndNumberIsLocal() {
        OtpPhoneNumber number = givenLocalPhoneNumber();
        Instant now = Instant.now();
        LastUpdatedConfig lastUpdatedConfig = mock(LastUpdatedConfig.class);
        Eligibility expectedEligibility = mock(Eligibility.class);
        given(lastUpdatedConfig.toEligibility(number, now)).willReturn(expectedEligibility);
        OtpPhoneNumberConfig config = OtpPhoneNumberConfig.builder()
                .allowInternational(false)
                .lastUpdatedConfig(lastUpdatedConfig)
                .build();

        Eligibility eligibility = config.toEligibility(number, now);

        assertThat(eligibility).isEqualTo(expectedEligibility);
    }

    @Test
    void shouldReturnInternationalNumbersNotAllowedIfInternationalNotAllowedAndNumberIsInternational() {
        OtpPhoneNumber number = givenInternationalPhoneNumber();
        Instant now = Instant.now();
        LastUpdatedConfig lastUpdatedConfig = mock(LastUpdatedConfig.class);
        OtpPhoneNumberConfig config = OtpPhoneNumberConfig.builder()
                .allowInternational(false)
                .lastUpdatedConfig(lastUpdatedConfig)
                .build();

        Eligibility eligibility = config.toEligibility(number, now);

        assertThat(eligibility).isInstanceOf(InternationalNumbersNotAllowed.class);
    }

    private OtpPhoneNumber givenLocalPhoneNumber() {
        OtpPhoneNumber number = mock(OtpPhoneNumber.class);
        given(number.isLocal()).willReturn(true);
        return number;
    }

    private OtpPhoneNumber givenInternationalPhoneNumber() {
        OtpPhoneNumber number = mock(OtpPhoneNumber.class);
        given(number.isLocal()).willReturn(false);
        return number;
    }

}
