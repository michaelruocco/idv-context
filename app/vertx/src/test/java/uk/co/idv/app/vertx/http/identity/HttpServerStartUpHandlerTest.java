package uk.co.idv.app.vertx.http.identity;

import io.vertx.core.AsyncResult;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpServer;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import uk.co.idv.app.vertx.http.HttpServerStartUpHandler;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class HttpServerStartUpHandlerTest {

    private final Promise<Void> promise = mock(Promise.class);
    private final HttpServerStartUpHandler handler = new HttpServerStartUpHandler();

    @Test
    void shouldCompletePromiseIfResultIsSuccessful() {
        HttpServer server = mock(HttpServer.class);
        SuccessfulResult result = new SuccessfulResult(server);

        handler.handle(result, promise);

        verify(promise).complete();
    }

    @Test
    void shouldFailPromiseWithCauseIfResultIsFailed() {
        Throwable cause = new Throwable();
        FailedResult result = new FailedResult(cause);

        handler.handle(result, promise);

        verify(promise).fail(cause);
    }

    @RequiredArgsConstructor
    private static class SuccessfulResult implements AsyncResult<HttpServer> {

        private final HttpServer server;

        @Override
        public HttpServer result() {
            return server;
        }

        @Override
        public Throwable cause() {
            return null;
        }

        @Override
        public boolean succeeded() {
            return true;
        }

        @Override
        public boolean failed() {
            return false;
        }

    }

    @RequiredArgsConstructor
    private static class FailedResult implements AsyncResult<HttpServer> {

        private final Throwable cause;

        @Override
        public HttpServer result() {
            return null;
        }

        @Override
        public Throwable cause() {
            return cause;
        }

        @Override
        public boolean succeeded() {
            return false;
        }

        @Override
        public boolean failed() {
            return true;
        }

    }

}
