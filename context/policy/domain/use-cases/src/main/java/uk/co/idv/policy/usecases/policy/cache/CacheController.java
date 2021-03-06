package uk.co.idv.policy.usecases.policy.cache;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.awaitility.Awaitility.await;

@Builder
@Slf4j
public class CacheController {

    @Builder.Default
    private final Duration maxWait = Duration.ofSeconds(3);

    @Builder.Default
    private final Duration pollDelay = Duration.ZERO;

    @Builder.Default
    private final Duration pollInterval = Duration.ofMillis(250);

    @Builder.Default
    private final AtomicBoolean updatingCache = new AtomicBoolean(false);

    public void startUpdate() {
        updatingCache.set(true);
    }

    public void completeUpdate() {
        updatingCache.set(false);
    }

    public void waitUntilUpdateComplete() {
        await().atMost(maxWait)
                .pollDelay(pollDelay)
                .pollInterval(pollInterval)
                .until(() -> !isUpdating());
    }

    public boolean isUpdating() {
        boolean refreshing = updatingCache.get();
        log.debug("cache refreshing during update {}", refreshing);
        return refreshing;
    }

}
