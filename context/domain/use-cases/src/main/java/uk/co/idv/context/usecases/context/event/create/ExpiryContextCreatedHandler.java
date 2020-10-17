package uk.co.idv.context.usecases.context.event.create;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.context.entities.context.Context;
import uk.co.idv.context.usecases.context.event.expiry.ExpiryHandler;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Builder
@Slf4j
public class ExpiryContextCreatedHandler implements ContextCreatedHandler {

    private final Clock clock;
    private final ScheduledExecutorService executor;

    public void created(Context context) {
        Duration delay = calculateDelay(context);
        executor.schedule(new ExpiryHandler(context), delay.toSeconds(), TimeUnit.SECONDS);
        log.info("scheduled context expiry event in {}s for context {}", delay.toSeconds(), context.getId());
    }

    private Duration calculateDelay(Context context) {
        Instant expiry = context.getExpiry();
        return Duration.between(clock.instant(), expiry);
    }

}
