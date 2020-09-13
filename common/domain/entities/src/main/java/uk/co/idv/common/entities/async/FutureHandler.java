package uk.co.idv.common.entities.async;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;

@Slf4j
public class FutureHandler {

    public static <T> T handle(CompletableFuture<T> future, T defaultValue) {
        return future
                .exceptionally(error -> handle(error, defaultValue))
                .getNow(defaultValue);
    }

    private static <T> T handle(Throwable error, T defaultValue) {
        log.debug(error.getMessage(), error);
        return defaultValue;
    }

}
