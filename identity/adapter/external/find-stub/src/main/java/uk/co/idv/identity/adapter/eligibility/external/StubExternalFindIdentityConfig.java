package uk.co.idv.identity.adapter.eligibility.external;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Builder
@Data
@Slf4j
public class StubExternalFindIdentityConfig implements ExternalFindIdentityConfig {

    private final ExecutorService executor;

    @Builder.Default private final Duration phoneNumberDelay = Duration.ofSeconds(0);
    @Builder.Default private final Duration emailAddressDelay = Duration.ofSeconds(0);

    @Getter(AccessLevel.NONE)
    @Builder.Default private final Duration timeout = Duration.ofSeconds(2);

    @Override
    public Duration getTimeout(String channelId) {
        return timeout;
    }

    public static StubExternalFindIdentityConfig buildExample() {
        return StubExternalFindIdentityConfig.builder()
                .executor(buildEligibilityExecutor())
                .timeout(Duration.ofMillis(250))
                .phoneNumberDelay(Duration.ofMillis(400))
                .emailAddressDelay(Duration.ofMillis(100))
                .build();
    }

    public static StubExternalFindIdentityConfig build(ExecutorService executor) {
        return StubExternalFindIdentityConfig.builder()
                .executor(executor)
                .build();
    }

    private static ExecutorService buildEligibilityExecutor() {
        return Executors.newFixedThreadPool(loadThreadPoolSize());
    }

    private static int loadThreadPoolSize() {
        String key = "external.find.identity.thread.pool.size";
        int size = Integer.parseInt(System.getProperty(key, "100"));
        log.info("loaded {} value {}", key, size);
        return size;
    }

}
