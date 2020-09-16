package uk.co.idv.context.adapter.context.method.otp.delivery.phone.simswap;

import lombok.Builder;
import uk.co.idv.common.usecases.async.Delay;
import uk.co.idv.context.usecases.context.method.otp.delivery.phone.simswap.SimSwapExecutor;

import java.time.Clock;
import java.time.Duration;
import java.util.concurrent.ExecutorService;

@Builder
public class StubSimSwapExecutorConfig {

    @Builder.Default
    private final Clock clock = Clock.systemUTC();

    @Builder.Default
    private final Delay delay = new Delay(Duration.ofSeconds(3));

    private final ExecutorService executorService;

    public SimSwapExecutor simSwapExecutor() {
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
                .build();
    }

}
