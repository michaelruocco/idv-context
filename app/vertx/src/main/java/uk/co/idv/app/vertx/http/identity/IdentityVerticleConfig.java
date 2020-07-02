package uk.co.idv.app.vertx.http.identity;

import lombok.RequiredArgsConstructor;
import uk.co.idv.config.identity.IdentityConfig;

@RequiredArgsConstructor
public class IdentityVerticleConfig {

    private final IdentityConfig config;

    public IdentityController identityController() {
        return new IdentityController(config.identityFacade());
    }



}
