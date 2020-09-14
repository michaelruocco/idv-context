package uk.co.idv.context.adapter.context.method.otp.delivery.phone.simswap;

import lombok.Builder;
import uk.co.idv.common.usecases.async.Delay;
import uk.co.idv.context.entities.policy.method.otp.delivery.phone.simswap.SimSwapConfig;
import uk.co.idv.context.usecases.context.method.otp.delivery.phone.simswap.SimSwapExecutor;

import java.time.Clock;
import java.util.concurrent.ExecutorService;

@Builder
public class StubSimSwapExecutorConfig {

    private final Clock clock;
    private final SimSwapConfig simSwapConfig;
    private final Delay delay;
    private final ExecutorService executorService;

    public SimSwapExecutor buildSimSwapExecutor() {
        return StubSimSwapExecutor.builder()
                .executor(executorService)
                .supplierFactory(buildSupplierFactory())
                .build();
    }

    private StubSimSwapEligibilitySupplierFactory buildSupplierFactory() {
        return StubSimSwapEligibilitySupplierFactory.builder()
                .resultFactory(buildResultFactory())
                .delay(delay)
                .clock(clock)
                .build();
    }

    private StubSimSwapResultFactory buildResultFactory() {
        return StubSimSwapResultFactory.builder()
                .clock(clock)
                .config(simSwapConfig)
                .build();
    }

}
