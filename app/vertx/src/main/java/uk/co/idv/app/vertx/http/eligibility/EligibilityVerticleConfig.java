package uk.co.idv.app.vertx.http.eligibility;

import lombok.RequiredArgsConstructor;
import uk.co.idv.config.identity.IdentityConfig;

@RequiredArgsConstructor
public class EligibilityVerticleConfig {

    private final IdentityConfig config;

    public EligibilityController eligibilityController() {
        return new EligibilityController(config.createEligibility());
    }

}
