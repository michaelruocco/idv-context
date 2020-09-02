package uk.co.idv.app.manual.identity;

import uk.co.idv.identity.config.IdentityConfig;
import uk.co.idv.identity.config.repository.IdentityRepositoryConfig;
import uk.co.idv.identity.config.repository.inmemory.InMemoryIdentityRepositoryConfig;

public class IdentityConfigBuilder {

    private final IdentityRepositoryConfig repositoryConfig = new InMemoryIdentityRepositoryConfig();

    public IdentityConfig build() {
        return IdentityConfig.builder()
                .repository(repositoryConfig.identityRepository())
                .build();
    }

}
