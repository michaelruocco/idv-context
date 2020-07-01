package uk.co.idv.app.vertx;

import com.fasterxml.jackson.databind.Module;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.json.jackson.DatabindCodec;
import lombok.RequiredArgsConstructor;
import uk.co.idv.app.vertx.identity.IdentityDeploymentOptions;
import uk.co.idv.app.vertx.identity.IdentityVerticle;
import uk.co.idv.context.adapter.json.eligibility.EligibilityModule;

@RequiredArgsConstructor
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
        Promise<String> deployment = Promise.promise();
        deployIdentity(deployment);
        deployment.future().succeeded();
    }

    private void deployIdentity(Promise<String> deployment) {
        vertx.deployVerticle(new IdentityVerticle(), new IdentityDeploymentOptions(), deployment);
    }

}
