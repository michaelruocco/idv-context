package uk.co.idv.common.entities.async;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;

import static org.assertj.core.api.Assertions.assertThat;

class FutureHandlerTest {

    @Test
    void shouldReturnResultFromFutureIfCompletedSuccessfully() {
        Object expected = new Object();
        Object defaultValue = new Object();
        CompletableFuture<Object> future = CompletableFuture.completedFuture(expected);

        Object actual = FutureHandler.handle(future, defaultValue);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void shouldReturnDefaultValueIfFutureNotCompletedSuccessfully() {
        Object defaultValue = new Object();
        CompletableFuture<Object> future = CompletableFuture.failedFuture(new Exception("test error"));

        Object actual = FutureHandler.handle(future, defaultValue);

        assertThat(actual).isEqualTo(defaultValue);
    }

}
