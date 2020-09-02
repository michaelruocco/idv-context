package uk.co.idv.app.manual.lockout;

import lombok.Builder;
import uk.co.idv.app.manual.identity.IdentityConfigBuilder;
import uk.co.idv.identity.config.IdentityConfig;
import uk.co.idv.context.config.lockout.LockoutConfig;
import uk.co.idv.context.config.lockout.repository.LockoutRepositoryConfig;
import uk.co.idv.context.config.lockout.repository.inmemory.InMemoryLockoutRepositoryConfig;

@Builder
public class LockoutConfigBuilder {

    @Builder.Default
    private final IdentityConfig identityConfig = new IdentityConfigBuilder().build();

    @Builder.Default
    private final LockoutRepositoryConfig repositoryConfig = new InMemoryLockoutRepositoryConfig();

    public LockoutConfig build() {
        return LockoutConfig.builder()
                .findIdentityProvider(identityConfig)
                .aliasFactory(identityConfig.aliasFactory())
                .attemptRepository(repositoryConfig.attemptRepository())
                .policyRepository(repositoryConfig.policyRepository())
                .build();
    }

}
