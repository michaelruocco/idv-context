package uk.co.idv.context.adapter.context.method.otp.delivery.phone.simswap;

import lombok.Builder;
import uk.co.idv.context.entities.context.method.otp.delivery.eligibility.AsyncFutureSimSwapEligibility;
import uk.co.idv.context.entities.policy.method.otp.delivery.phone.OtpPhoneNumber;
import uk.co.idv.context.entities.policy.method.otp.delivery.phone.simswap.SimSwapConfig;
import uk.co.idv.context.usecases.context.method.otp.delivery.phone.simswap.SimSwapExecutor;
import uk.co.idv.method.entities.eligibility.Eligibility;

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
