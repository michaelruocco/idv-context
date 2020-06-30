package uk.co.idv.app.vertx.identity;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class IdentityVerticle extends AbstractVerticle {

    private static final String URI = "/identities";

    private final IdentityVerticleConfig config;

    @Override
    public void start(Promise<Void> promise) {
        Router router = router();
        vertx.createHttpServer()
                .requestHandler(router)
                .listen(getPort(), result -> handleStartUp(result, promise));
    }

    private int getPort() {
        return config().getInteger("http.port", 8081);
    }

    private Router router() {
        IdentityController controller = config.identityController();
        Router router = Router.router(vertx);
        router.route().handler(BodyHandler.create());
        router.get(URI).handler(controller::getIdentity);
        router.post(URI).handler(controller::updateIdentity);
        router.errorHandler(500, config.errorHandler());
        return router;
    }

    private void handleStartUp(AsyncResult<HttpServer> result, Promise<Void> promise) {
        if (result.succeeded()) {
            success(promise);
            return;
        }
        failure(result, promise);
    }

    private void success(Promise<Void> promise) {
        log.info("Identity verticle running on port " + getPort());
        promise.complete();
    }

    private void failure(AsyncResult<HttpServer> result, Promise<Void> promise) {
        log.error("Could not start identity verticle", result.cause());
        promise.fail(result.cause());
    }

}
