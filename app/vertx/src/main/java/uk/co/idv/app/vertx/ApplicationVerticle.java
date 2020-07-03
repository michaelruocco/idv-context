package uk.co.idv.app.vertx;

import com.fasterxml.jackson.databind.Module;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.json.jackson.DatabindCodec;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.app.vertx.http.HttpServerVerticle;
import uk.co.idv.app.vertx.http.HttpServerOptions;
import uk.co.idv.config.identity.IdentityConfig;
import uk.co.idv.context.adapter.eligibility.external.ExternalFindIdentityStubConfig;
import uk.co.idv.context.adapter.json.VerificationContextParentModule;

import java.time.Duration;
import java.util.concurrent.Executors;

@RequiredArgsConstructor
@Slf4j
public class ApplicationVerticle extends AbstractVerticle {

    private final Module module;

    public ApplicationVerticle() {
        this(new VerificationContextParentModule());
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
        IdentityConfig config = identityConfig();
        return vertx.deployVerticle(() -> new HttpServerVerticle(config), new HttpServerOptions());
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
