package uk.co.idv.app.vertx.http.eligibility;

import io.vertx.ext.web.Router;
import lombok.RequiredArgsConstructor;
import uk.co.idv.app.vertx.http.RouterPopulator;

@RequiredArgsConstructor
public class EligibilityRouterPopulator implements RouterPopulator {

    private static final String URI = "/eligibility";

    private final EligibilityVerticleConfig config;

    @Override
    public void populate(Router router) {
        EligibilityController controller = config.eligibilityController();
        router.post(URI).handler(controller::createEligibility);
    }

}
