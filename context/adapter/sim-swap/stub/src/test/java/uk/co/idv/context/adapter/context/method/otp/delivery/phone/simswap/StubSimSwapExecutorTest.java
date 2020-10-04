package uk.co.idv.context.adapter.context.method.otp.delivery.phone.simswap;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.context.method.otp.delivery.eligibility.AsyncFutureSimSwapEligibility;
import uk.co.idv.context.entities.policy.method.otp.delivery.phone.OtpPhoneNumber;
import uk.co.idv.context.entities.policy.method.otp.delivery.phone.simswap.SimSwapConfig;
import uk.co.idv.method.entities.eligibility.Eligibility;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class StubSimSwapExecutorTest {

    private final StubSimSwapEligibilitySupplierFactory supplierFactory = mock(StubSimSwapEligibilitySupplierFactory.class);
    private final Executor executor = Executors.newSingleThreadExecutor();

    private final OtpPhoneNumber number = mock(OtpPhoneNumber.class);
    private final SimSwapConfig config = mock(SimSwapConfig.class);

    private final StubSimSwapExecutor simSwapExecutor = StubSimSwapExecutor.builder()
            .supplierFactory(supplierFactory)
            .executor(executor)
            .build();

    @Test
    void shouldPopulateEligibilityWithConfig() {
        givenSupplierCreated();

        AsyncFutureSimSwapEligibility eligibility = simSwapExecutor.executeSimSwap(number, config);

        assertThat(eligibility.getConfig()).isEqualTo(config);
    }

    @Test
    void shouldPopulateEligibilityFromSupplier() throws ExecutionException, InterruptedException {
        Eligibility expectedEligibility = givenEligibilityReturned();

        AsyncFutureSimSwapEligibility eligibility = simSwapExecutor.executeSimSwap(number, config);

        assertThat(eligibility.getFuture().get()).isEqualTo(expectedEligibility);
    }

    private Eligibility givenEligibilityReturned() {
        StubSimSwapEligibilitySupplier supplier = givenSupplierCreated();
        Eligibility eligibility = mock(Eligibility.class);
        given(supplier.get()).willReturn(eligibility);
        return eligibility;
    }

    private StubSimSwapEligibilitySupplier givenSupplierCreated() {
        StubSimSwapEligibilitySupplier supplier = mock(StubSimSwapEligibilitySupplier.class);
        given(supplierFactory.toSupplier(number, config)).willReturn(supplier);
        return supplier;
    }

}
