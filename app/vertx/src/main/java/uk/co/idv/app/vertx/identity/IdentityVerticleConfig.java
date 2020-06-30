package uk.co.idv.app.vertx.identity;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import lombok.RequiredArgsConstructor;
import uk.co.idv.config.identity.IdentityConfig;
import uk.co.idv.context.adapter.eligibility.external.ExternalFindIdentityStubConfig;

import java.time.Duration;
import java.util.concurrent.Executors;

@RequiredArgsConstructor
public class IdentityVerticleConfig {

    private final IdentityConfig config;

    public IdentityVerticleConfig() {
        this(identityConfig());
    }

    public IdentityController identityController() {
        return new IdentityController(config.identityFacade());
    }

    public DeploymentOptions deploymentOptions() {
        return new IdentityDeploymentOptions();
    }

    public Handler<RoutingContext> errorHandler() {
        return new IdentityExceptionHandler();
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
