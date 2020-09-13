package uk.co.idv.common.usecases.async;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class FutureWaiterTest {

    private final Duration timeout = Duration.ofMillis(1);

    private final FutureWaiter waiter = new FutureWaiter();

    @Test
    void shouldHandleInterruptedException() throws InterruptedException, ExecutionException, TimeoutException {
        CompletableFuture<Void> future = givenFutureThrows(new InterruptedException("test error"));

        assertThatCode(() -> waiter.waitFor(future, timeout)).doesNotThrowAnyException();
    }

    @Test
    void shouldHandleExecutionException() throws InterruptedException, ExecutionException, TimeoutException {
        CompletableFuture<Void> future = givenFutureThrows(new ExecutionException(new Exception("test error")));

        assertThatCode(() -> waiter.waitFor(future, timeout)).doesNotThrowAnyException();
    }

    @Test
    void shouldHandleTimeoutException() throws InterruptedException, ExecutionException, TimeoutException {
        CompletableFuture<Void> future = givenFutureThrows(new TimeoutException("test error"));

        assertThatCode(() -> waiter.waitFor(future, timeout)).doesNotThrowAnyException();
    }

    private CompletableFuture<Void> givenFutureThrows(Exception exception) throws InterruptedException, ExecutionException, TimeoutException {
        CompletableFuture<Void> future = mock(CompletableFuture.class);
        given(future.get(timeout.toMillis(), TimeUnit.MILLISECONDS)).willThrow(exception);
        return future;
    }

}
