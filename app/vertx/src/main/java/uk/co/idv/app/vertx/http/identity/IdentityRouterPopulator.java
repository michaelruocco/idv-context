package uk.co.idv.app.vertx.http.identity;

import io.vertx.ext.web.Router;
import lombok.RequiredArgsConstructor;
import uk.co.idv.app.vertx.http.RouterPopulator;

@RequiredArgsConstructor
public class IdentityRouterPopulator implements RouterPopulator {

    private static final String URI = "/identities";
    private static final String GET_BY_ID_URI = URI + "/:idvId";

    private final IdentityVerticleConfig config;

    @Override
    public void populate(Router router) {
        IdentityController controller = config.identityController();
        router.get(URI).handler(controller::getIdentityByAlias);
        router.post(URI).handler(controller::updateIdentity);
        router.get(GET_BY_ID_URI).handler(controller::getIdentityByIdvId);
    }

}
