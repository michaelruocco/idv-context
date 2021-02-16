package uk.co.idv.common.usecases.async;

import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static uk.co.mruoc.duration.calculator.DurationCalculatorUtils.millisBetweenNowAnd;

@Slf4j
public class FutureWaiter {

    public void waitFor(CompletableFuture<Void> future, Duration timeout) {
        log.info("waiting for future to complete with timeout {}", timeout);
        Instant start = Instant.now();
        try {
            future.get(timeout.toMillis(), TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.debug("thread interrupted waiting for future to complete", e);
        } catch (ExecutionException | TimeoutException e) {
            log.error("an error occurred executing future", e);
        } finally {
            log.info("future took {}ms to complete", millisBetweenNowAnd(start));
        }
    }

}
