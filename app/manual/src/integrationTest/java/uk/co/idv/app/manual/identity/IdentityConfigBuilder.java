package uk.co.idv.app.manual.identity;

import uk.co.idv.context.config.identity.IdentityConfig;
import uk.co.idv.context.config.identity.respository.IdentityRepositoryConfig;
import uk.co.idv.context.config.identity.respository.inmemory.InMemoryIdentityRepositoryConfig;

public class IdentityConfigBuilder {

    private final IdentityRepositoryConfig repositoryConfig = new InMemoryIdentityRepositoryConfig();

    public IdentityConfig build() {
        return IdentityConfig.builder()
                .repository(repositoryConfig.identityRepository())
                .build();
    }

}
