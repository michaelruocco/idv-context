package uk.co.idv.method.usecases.otp.delivery.phone;

import org.junit.jupiter.api.Test;
import uk.co.idv.method.entities.eligibility.Eligibility;
import uk.co.idv.method.entities.otp.delivery.phone.OtpPhoneNumber;
import uk.co.idv.method.entities.otp.delivery.phone.OtpPhoneNumberMother;
import uk.co.idv.method.entities.otp.policy.delivery.phone.PhoneDeliveryMethodConfig;
import uk.co.idv.method.entities.otp.policy.delivery.phone.SimSwapConfig;
import uk.co.idv.method.entities.otp.simswap.eligibility.AsyncFutureSimSwapEligibility;
import uk.co.idv.method.usecases.otp.delivery.phone.simswap.SimSwapExecutor;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

class OtpPhoneNumberEligibilityCalculatorTest {

    private static final Instant NOW = Instant.now();

    private final Clock clock = Clock.fixed(NOW, ZoneId.systemDefault());
    private final SimSwapExecutor simSwapExecutor = mock(SimSwapExecutor.class);

    private final OtpPhoneNumberEligibilityCalculator calculator = OtpPhoneNumberEligibilityCalculator.builder()
            .clock(clock)
            .simSwapExecutor(simSwapExecutor)
            .build();

    @Test
    void shouldReturnEligibilityFromConfigWithoutSimSwapIfNotEligible() {
        PhoneDeliveryMethodConfig config = mock(PhoneDeliveryMethodConfig.class);
        OtpPhoneNumber phoneNumber = OtpPhoneNumberMother.localMobile();
        Eligibility expectedEligibility = givenEligibilityReturnedFromConfig(phoneNumber, config);
        given(expectedEligibility.isEligible()).willReturn(false);

        Eligibility eligibility = calculator.toEligibility(phoneNumber, config);

        assertThat(eligibility).isEqualTo(expectedEligibility);
        verify(simSwapExecutor, never()).executeSimSwap(any(OtpPhoneNumber.class), any(SimSwapConfig.class));
    }

    @Test
    void shouldReturnEligibilityFromConfigWithoutSimSwapIfEligibleButNoSimSwapConfiguration() {
        PhoneDeliveryMethodConfig config = mock(PhoneDeliveryMethodConfig.class);
        OtpPhoneNumber phoneNumber = OtpPhoneNumberMother.localMobile();
        Eligibility expectedEligibility = givenEligibilityReturnedFromConfig(phoneNumber, config);
        given(expectedEligibility.isEligible()).willReturn(true);

        Eligibility eligibility = calculator.toEligibility(phoneNumber, config);

        assertThat(eligibility).isEqualTo(expectedEligibility);
        verify(simSwapExecutor, never()).executeSimSwap(any(OtpPhoneNumber.class), any(SimSwapConfig.class));
    }

    @Test
    void shouldReturnEligibilityFromSimSwapIfEligibleAndSimSwapConfiguration() {
        PhoneDeliveryMethodConfig deliveryMethodConfig = mock(PhoneDeliveryMethodConfig.class);
        SimSwapConfig simSwapConfig = mock(SimSwapConfig.class);
        given(deliveryMethodConfig.getSimSwapConfig()).willReturn(Optional.of(simSwapConfig));
        OtpPhoneNumber phoneNumber = OtpPhoneNumberMother.localMobile();
        Eligibility configEligibility = givenEligibilityReturnedFromConfig(phoneNumber, deliveryMethodConfig);
        given(configEligibility.isEligible()).willReturn(true);
        Eligibility simSwapEligibility = givenEligibilityReturnedFromSimSwap(phoneNumber, simSwapConfig);

        Eligibility eligibility = calculator.toEligibility(phoneNumber, deliveryMethodConfig);

        assertThat(eligibility).isEqualTo(simSwapEligibility);
    }

    private Eligibility givenEligibilityReturnedFromConfig(OtpPhoneNumber phoneNumber, PhoneDeliveryMethodConfig config) {
        Eligibility eligibility = mock(Eligibility.class);
        given(config.toEligibility(phoneNumber, NOW)).willReturn(eligibility);
        return eligibility;
    }

    private Eligibility givenEligibilityReturnedFromSimSwap(OtpPhoneNumber phoneNumber, SimSwapConfig config) {
        AsyncFutureSimSwapEligibility eligibility = mock(AsyncFutureSimSwapEligibility.class);
        given(simSwapExecutor.executeSimSwap(phoneNumber, config)).willReturn(eligibility);
        return eligibility;
    }

}
