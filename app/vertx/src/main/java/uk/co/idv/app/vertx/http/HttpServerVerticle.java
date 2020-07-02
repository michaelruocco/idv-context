package uk.co.idv.app.vertx.http;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.config.identity.IdentityConfig;
import uk.co.idv.context.adapter.eligibility.external.ExternalFindIdentityStubConfig;

import java.time.Duration;
import java.util.concurrent.Executors;

import static uk.co.idv.app.vertx.PortLoader.loadPort;

@Slf4j
@RequiredArgsConstructor
public class HttpServerVerticle extends AbstractVerticle {

    private final RouterPopulator routerPopulator;
    private final HttpServerStartUpHandler startUpHandler;
    private final ExceptionHandler exceptionHandler;

    public HttpServerVerticle() {
        this(new CompositeRouterPopulator(identityConfig()),
                new HttpServerStartUpHandler(),
                new ExceptionHandler());
    }

    @Override
    public void start(Promise<Void> promise) {
        Router router = Router.router(vertx);
        router.route().handler(BodyHandler.create());
        routerPopulator.populate(router);
        router.errorHandler(500, exceptionHandler);
        vertx.createHttpServer()
                .requestHandler(router)
                .listen(loadPort(), result -> startUpHandler.handle(result, promise));
    }

    private static IdentityConfig identityConfig() {
        return IdentityConfig.builder()
                .stubConfig(stubConfig())
                .build();
    }

    private static ExternalFindIdentityStubConfig stubConfig() {
        return ExternalFindIdentityStubConfig.builder()
                .executor(Executors.newFixedThreadPool(2))
                .timeout(Duration.ofMillis(250))
                .phoneNumberDelay(Duration.ofMillis(400))
                .emailAddressDelay(Duration.ofMillis(100))
                .build();
    }

}
