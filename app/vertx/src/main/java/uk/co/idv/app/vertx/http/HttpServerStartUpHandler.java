package uk.co.idv.app.vertx.http;

import io.vertx.core.AsyncResult;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpServer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class HttpServerStartUpHandler {

    public void handle(AsyncResult<HttpServer> result, Promise<Void> promise) {
        if (result.succeeded()) {
            success(result, promise);
            return;
        }
        failure(result, promise);
    }

    private void success(AsyncResult<HttpServer> result, Promise<Void> promise) {
        log.info("http server verticle running on port {}", result.result().actualPort());
        promise.complete();
    }

    private void failure(AsyncResult<HttpServer> result, Promise<Void> promise) {
        log.error("could not start http server verticle", result.cause());
        promise.fail(result.cause());
    }

}
