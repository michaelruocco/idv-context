package uk.co.idv.context.adapter.context.method.otp.delivery.phone.simswap;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.common.entities.async.Delay;
import uk.co.idv.method.usecases.otp.delivery.phone.simswap.SimSwapExecutor;
import uk.co.idv.method.usecases.otp.delivery.phone.simswap.SimSwapExecutorConfig;

import java.time.Clock;
import java.time.Duration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Builder
@Slf4j
public class StubSimSwapExecutorConfig implements SimSwapExecutorConfig {

    @Builder.Default
    private final Delay delay = new Delay(Duration.ofMillis(3500));

    private final Clock clock;
    private final ExecutorService executor;

    public static StubSimSwapExecutorConfig buildDefault() {
        return buildDefault(Clock.systemUTC());
    }

    public static StubSimSwapExecutorConfig buildDefault(Clock clock) {
        return StubSimSwapExecutorConfig.builder()
                .executor(buildSimSwapExecutor())
                .clock(clock)
                .build();
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
                .delay(delay)
                .clock(clock)
                .build();
    }

    private StubSimSwapResultFactory buildResultFactory() {
        return StubSimSwapResultFactory.builder()
                .clock(clock)
                .build();
    }

    private static ExecutorService buildSimSwapExecutor() {
        return Executors.newFixedThreadPool(loadThreadPoolSize());
    }

    private static int loadThreadPoolSize() {
        String key = "sim.swap.executor.thread.pool.size";
        int size = Integer.parseInt(System.getProperty(key, "100"));
        log.info("loaded {} value {}", key, size);
        return size;
    }

}
