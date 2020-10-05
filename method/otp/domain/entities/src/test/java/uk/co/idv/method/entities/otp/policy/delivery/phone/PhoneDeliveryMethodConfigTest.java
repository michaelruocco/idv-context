package uk.co.idv.context.entities.policy.method.otp.delivery.phone;

import com.neovisionaries.i18n.CountryCode;
import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.policy.method.otp.delivery.phone.simswap.SimSwapConfig;
import uk.co.idv.context.entities.policy.method.otp.delivery.phone.simswap.SimSwapConfigMother;
import uk.co.idv.identity.entities.identity.RequestedData;

import java.time.Duration;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class PhoneDeliveryMethodConfigTest {

    private static final String TYPE = "my-type";

    private final OtpPhoneNumberConfig phoneNumberConfig = mock(OtpPhoneNumberConfig.class);

    private final PhoneDeliveryMethodConfig config = new PhoneDeliveryMethodConfig(TYPE, phoneNumberConfig);

    @Test
    void shouldReturnType() {
        assertThat(config.getType()).isEqualTo(TYPE);
    }

    @Test
    void shouldRequestPhoneNumbers() {
        assertThat(config.getRequestedData()).isEqualTo(RequestedData.phoneNumbersOnly());
    }

    @Test
    void shouldReturnEmptySimSwapConfigIfNotConfigured() {
        given(phoneNumberConfig.getSimSwapConfig()).willReturn(Optional.empty());

        Optional<SimSwapConfig> simSwapConfig = config.getSimSwapConfig();

        assertThat(simSwapConfig).isEmpty();
    }

    @Test
    void shouldReturnSimSwapConfigFromPhoneNumberConfigIfConfigured() {
        SimSwapConfig expectedSimSwapConfig = SimSwapConfigMother.build();
        given(phoneNumberConfig.getSimSwapConfig()).willReturn(Optional.of(expectedSimSwapConfig));

        Optional<SimSwapConfig> simSwapConfig = config.getSimSwapConfig();

        assertThat(simSwapConfig).contains(expectedSimSwapConfig);
    }

    @Test
    void shouldReturnEmptySimSwapTimeoutIfNotConfigured() {
        given(phoneNumberConfig.getSimSwapConfig()).willReturn(Optional.empty());

        Optional<Duration> timeout = config.getSimSwapTimeout();

        assertThat(timeout).isEmpty();
    }

    @Test
    void shouldReturnSimSwapTimeoutFromPhoneNumberConfigIfConfigured() {
        SimSwapConfig simSwapConfig = SimSwapConfigMother.build();
        given(phoneNumberConfig.getSimSwapConfig()).willReturn(Optional.of(simSwapConfig));

        Optional<Duration> timeout = config.getSimSwapTimeout();

        assertThat(timeout).contains(simSwapConfig.getTimeout());
    }

    @Test
    void shouldReturnCountryFromPhoneNumberConfig() {
        CountryCode expectedCountry = CountryCode.GB;
        given(phoneNumberConfig.getCountry()).willReturn(expectedCountry);

        CountryCode country = config.getCountry();

        assertThat(country).isEqualTo(expectedCountry);
    }

}
