package uk.co.idv.context.adapter.context.method.otp.delivery.phone.simswap;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.context.eligibility.Eligibility;
import uk.co.idv.context.entities.context.method.otp.delivery.eligibility.AsyncSimSwapEligibility;
import uk.co.idv.context.entities.policy.method.otp.delivery.phone.OtpPhoneNumber;
import uk.co.idv.context.entities.policy.method.otp.delivery.phone.simswap.SimSwapConfig;

import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class StubSimSwapExecutorTest {

    private final StubSimSwapEligibilitySupplierFactory supplierFactory = mock(StubSimSwapEligibilitySupplierFactory.class);

    private final StubSimSwapExecutor executor = new StubSimSwapExecutor(supplierFactory);

    @Test
    void shouldPopulateEligibilityWithConfig() {
        OtpPhoneNumber number = mock(OtpPhoneNumber.class);
        SimSwapConfig config = mock(SimSwapConfig.class);
        givenSupplierCreatedFor(number);

        AsyncSimSwapEligibility eligibility = executor.performSimSwap(number, config);

        assertThat(eligibility.getConfig()).isEqualTo(config);
    }

    @Test
    void shouldPopulateEligibilityFromSupplier() throws ExecutionException, InterruptedException {
        OtpPhoneNumber number = mock(OtpPhoneNumber.class);
        SimSwapConfig config = mock(SimSwapConfig.class);
        Eligibility expectedEligibility = givenEligibilityReturnedFor(number);

        AsyncSimSwapEligibility eligibility = executor.performSimSwap(number, config);

        assertThat(eligibility.getFuture().get()).isEqualTo(expectedEligibility);
    }

    private Eligibility givenEligibilityReturnedFor(OtpPhoneNumber number) {
        StubSimSwapEligibilitySupplier supplier = givenSupplierCreatedFor(number);
        Eligibility eligibility = mock(Eligibility.class);
        given(supplier.get()).willReturn(eligibility);
        return eligibility;
    }

    private StubSimSwapEligibilitySupplier givenSupplierCreatedFor(OtpPhoneNumber number) {
        StubSimSwapEligibilitySupplier supplier = mock(StubSimSwapEligibilitySupplier.class);
        given(supplierFactory.toSupplier(number)).willReturn(supplier);
        return supplier;
    }
}
