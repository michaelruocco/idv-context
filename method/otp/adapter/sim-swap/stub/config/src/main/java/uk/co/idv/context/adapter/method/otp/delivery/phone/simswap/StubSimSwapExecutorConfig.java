package uk.co.idv.context.adapter.method.otp.delivery.phone.simswap;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.common.entities.async.Delay;
import uk.co.idv.method.usecases.otp.delivery.phone.simswap.SimSwapExecutor;
import uk.co.idv.method.usecases.otp.delivery.phone.simswap.SimSwapExecutorConfig;

import java.time.Clock;
import java.time.Duration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

@Builder
@Slf4j
public class StubSimSwapExecutorConfig implements SimSwapExecutorConfig {

    private final Supplier<Delay> delaySupplier;
    private final Clock clock;
    private final ExecutorService executor;

    public static SimSwapExecutorConfig build() {
        return defaultBuilder().build();
    }

    public static SimSwapExecutorConfig withFixedDelay() {
        return defaultBuilder()
                .delaySupplier(() -> new Delay(Duration.ofMillis(3500)))
                .build();
    }

    public static StubSimSwapExecutorConfigBuilder defaultBuilder() {
        return StubSimSwapExecutorConfig.builder()
                .delaySupplier(new StubSimSwapDelaySupplier())
                .executor(buildSimSwapExecutor())
                .clock(Clock.systemUTC());
    }

    public SimSwapExecutor simSwapExecutor() {
        return StubSimSwapExecutor.builder()
                .executor(executor)
                .supplierFactory(buildSupplierFactory())
                .build();
    }

    private StubSimSwapEligibilitySupplierFactory buildSupplierFactory() {
        return StubSimSwapEligibilitySupplierFactory.builder()
                .resultFactory(buildResultFactory())
                .delaySupplier(delaySupplier)
                .clock(clock)
                .build();
    }

    private StubSimSwapResultFactory buildResultFactory() {
        return StubSimSwapResultFactory.builder()
                .clock(clock)
                .build();
    }

    private static ExecutorService buildSimSwapExecutor() {
        return Executors.newCachedThreadPool();
    }

}
