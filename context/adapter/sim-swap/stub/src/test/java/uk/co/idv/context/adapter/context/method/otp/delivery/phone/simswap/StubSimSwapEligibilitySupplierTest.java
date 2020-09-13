package uk.co.idv.context.adapter.context.method.otp.delivery.phone.simswap;

import org.junit.jupiter.api.Test;
import uk.co.idv.common.usecases.async.Delay;
import uk.co.idv.context.entities.context.eligibility.Eligibility;
import uk.co.idv.context.entities.policy.method.otp.delivery.phone.OtpPhoneNumber;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

class StubSimSwapEligibilitySupplierTest {

    private static final Instant NOW = Instant.now();

    private final StubSimSwapResultFactory resultFactory = mock(StubSimSwapResultFactory.class);
    private final OtpPhoneNumber number = mock(OtpPhoneNumber.class);
    private final Delay delay = mock(Delay.class);
    private final Clock clock = Clock.fixed(NOW, ZoneId.systemDefault());

    private final StubSimSwapEligibilitySupplier supplier = StubSimSwapEligibilitySupplier.builder()
            .resultFactory(resultFactory)
            .number(number)
            .delay(delay)
            .clock(clock)
            .build();

    @Test
    void shouldReturnResultFactory() {
        assertThat(supplier.getResultFactory()).isEqualTo(resultFactory);
    }

    @Test
    void shouldReturnNumber() {
        assertThat(supplier.getNumber()).isEqualTo(number);
    }

    @Test
    void shouldReturnDelay() {
        assertThat(supplier.getDelay()).isEqualTo(delay);
    }

    @Test
    void shouldReturnClock() {
        assertThat(supplier.getClock()).isEqualTo(clock);
    }

    @Test
    void shouldBeDelayedIfNumberHasFiveAsLastDigit() {
        givenSimSwapResult();
        given(number.getLastDigit()).willReturn(5);

        supplier.get();

        verify(delay).execute();
    }

    @Test
    void shouldNotBeDelayedIfNumberDoesNotHaveFiveAsLastDigit() {
        givenSimSwapResult();
        given(number.getLastDigit()).willReturn(1);

        supplier.get();

        verify(delay, never()).execute();
    }

    @Test
    void shouldReturnEligibilityFromResultReturnedFromSupplier() {
        Eligibility expectedEligibility = givenSimSwapResultWithEligibility();

        Eligibility eligibility = supplier.get();

        assertThat(eligibility).isEqualTo(expectedEligibility);
    }

    private SimSwapResult givenSimSwapResult() {
        SimSwapResult result = mock(SimSwapResult.class);
        given(resultFactory.build(number)).willReturn(result);
        return result;
    }

    private Eligibility givenSimSwapResultWithEligibility() {
        SimSwapResult result = givenSimSwapResult();
        Eligibility eligibility = mock(Eligibility.class);
        given(result.toEligibility(NOW)).willReturn(eligibility);
        return eligibility;
    }

}
