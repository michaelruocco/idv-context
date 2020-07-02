package uk.co.idv.app.vertx;

import com.fasterxml.jackson.databind.Module;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.json.jackson.DatabindCodec;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.app.vertx.http.HttpServerVerticle;
import uk.co.idv.app.vertx.http.HttpServerOptions;
import uk.co.idv.context.adapter.json.eligibility.EligibilityModule;

@RequiredArgsConstructor
@Slf4j
public class ApplicationVerticle extends AbstractVerticle {

    private final Module module;

    public ApplicationVerticle() {
        this(new EligibilityModule());
    }

    @Override
    public void start() {
        registerJacksonModule();
        deploy();
    }

    private void registerJacksonModule() {
        DatabindCodec.mapper().registerModules(module);
        DatabindCodec.prettyMapper().registerModules(module);
    }

    private void deploy() {
        Future<String> future = deployHttpServer();
        future.succeeded();
    }

    private Future<String> deployHttpServer() {
        return vertx.deployVerticle(HttpServerVerticle.class, new HttpServerOptions());
    }

}
