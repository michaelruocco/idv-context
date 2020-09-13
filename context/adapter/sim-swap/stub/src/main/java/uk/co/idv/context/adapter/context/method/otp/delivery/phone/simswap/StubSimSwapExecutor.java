package uk.co.idv.context.adapter.context.method.otp.delivery.phone.simswap;

import lombok.Builder;
import uk.co.idv.context.entities.context.eligibility.Eligibility;
import uk.co.idv.context.entities.context.method.otp.delivery.eligibility.AsyncSimSwapEligibility;
import uk.co.idv.context.entities.policy.method.otp.delivery.phone.OtpPhoneNumber;
import uk.co.idv.context.entities.policy.method.otp.delivery.phone.simswap.SimSwapConfig;
import uk.co.idv.context.usecases.context.method.otp.delivery.phone.simswap.SimSwapExecutor;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Supplier;

@Builder
public class StubSimSwapExecutor implements SimSwapExecutor {

    private final StubSimSwapEligibilitySupplierFactory supplierFactory;
    private final Executor executor;

    @Override
    public AsyncSimSwapEligibility executeSimSwap(OtpPhoneNumber number, SimSwapConfig config) {
        Supplier<Eligibility> supplier = supplierFactory.toSupplier(number);
        return AsyncSimSwapEligibility.builder()
                .config(config)
                .future(CompletableFuture.supplyAsync(supplier, executor))
                .build();
    }

}
