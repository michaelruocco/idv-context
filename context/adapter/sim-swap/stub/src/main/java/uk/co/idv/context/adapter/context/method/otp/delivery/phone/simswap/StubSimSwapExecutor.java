package uk.co.idv.context.adapter.context.method.otp.delivery.phone.simswap;

import lombok.RequiredArgsConstructor;
import uk.co.idv.context.entities.context.eligibility.Eligibility;
import uk.co.idv.context.entities.context.method.otp.delivery.eligibility.AsyncSimSwapEligibility;
import uk.co.idv.context.entities.policy.method.otp.delivery.phone.OtpPhoneNumber;
import uk.co.idv.context.entities.policy.method.otp.delivery.phone.simswap.SimSwapConfig;
import uk.co.idv.context.usecases.context.method.otp.delivery.phone.simswap.SimSwapExecutor;

import java.util.function.Supplier;

import static java.util.concurrent.CompletableFuture.completedFuture;

@RequiredArgsConstructor
public class StubSimSwapExecutor implements SimSwapExecutor {

    private final StubSimSwapEligibilitySupplierFactory supplierFactory;

    @Override
    public AsyncSimSwapEligibility performSimSwap(OtpPhoneNumber number, SimSwapConfig config) {
        Supplier<Eligibility> supplier = supplierFactory.toSupplier(number);
        return AsyncSimSwapEligibility.builder()
                .config(config)
                .future(completedFuture(supplier.get()))
                .build();
    }

}
