package uk.co.idv.app.vertx.http;

import io.vertx.ext.web.Router;
import lombok.RequiredArgsConstructor;
import uk.co.idv.app.vertx.http.eligibility.EligibilityRouterPopulator;
import uk.co.idv.app.vertx.http.eligibility.EligibilityVerticleConfig;
import uk.co.idv.app.vertx.http.identity.IdentityRouterPopulator;
import uk.co.idv.app.vertx.http.identity.IdentityVerticleConfig;
import uk.co.idv.config.identity.IdentityConfig;

import java.util.Arrays;
import java.util.Collection;

@RequiredArgsConstructor
public class CompositeRouterPopulator implements RouterPopulator {

    private final Collection<RouterPopulator> populators;

    public CompositeRouterPopulator(IdentityConfig config) {
        this(Arrays.asList(
                new IdentityRouterPopulator(new IdentityVerticleConfig(config)),
                new EligibilityRouterPopulator(new EligibilityVerticleConfig(config))
        ));
    }

    @Override
    public void populate(Router router) {
        populators.forEach(populator -> populator.populate(router));
    }

}
