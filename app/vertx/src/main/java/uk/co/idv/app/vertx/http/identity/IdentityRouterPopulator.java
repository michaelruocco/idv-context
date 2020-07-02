package uk.co.idv.app.vertx.http.identity;

import io.vertx.ext.web.Router;
import lombok.RequiredArgsConstructor;
import uk.co.idv.app.vertx.http.RouterPopulator;

@RequiredArgsConstructor
public class IdentityRouterPopulator implements RouterPopulator {

    private static final String URI = "/identities";

    private final IdentityVerticleConfig config;

    @Override
    public void populate(Router router) {
        IdentityController controller = config.identityController();
        router.get(IdentityRouterPopulator.URI).handler(controller::getIdentity);
        router.post(IdentityRouterPopulator.URI).handler(controller::updateIdentity);
    }

}
