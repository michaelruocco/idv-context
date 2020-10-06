package uk.co.idv.context.adapter.context.method.otp.delivery.phone.simswap;

import lombok.Builder;
import uk.co.idv.method.entities.eligibility.Eligibility;
import uk.co.idv.method.entities.otp.delivery.phone.OtpPhoneNumber;
import uk.co.idv.method.entities.otp.policy.delivery.phone.SimSwapConfig;
import uk.co.idv.method.entities.otp.simswap.eligibility.AsyncFutureSimSwapEligibility;
import uk.co.idv.method.usecases.otp.delivery.phone.simswap.SimSwapExecutor;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Supplier;

@Builder
public class StubSimSwapExecutor implements SimSwapExecutor {

    private final StubSimSwapEligibilitySupplierFactory supplierFactory;
    private final Executor executor;

    @Override
    public AsyncFutureSimSwapEligibility executeSimSwap(OtpPhoneNumber number, SimSwapConfig config) {
        Supplier<Eligibility> supplier = supplierFactory.toSupplier(number, config);
        return AsyncFutureSimSwapEligibility.builder()
                .config(config)
                .future(CompletableFuture.supplyAsync(supplier, executor))
                .build();
    }

}
