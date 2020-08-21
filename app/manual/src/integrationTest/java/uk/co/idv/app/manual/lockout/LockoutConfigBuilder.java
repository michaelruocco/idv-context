package uk.co.idv.app.manual.lockout;

import lombok.RequiredArgsConstructor;
import uk.co.idv.app.manual.identity.IdentityConfigBuilder;
import uk.co.idv.context.config.identity.IdentityConfig;
import uk.co.idv.context.config.lockout.LockoutConfig;
import uk.co.idv.context.config.lockout.repository.LockoutRepositoryConfig;
import uk.co.idv.context.config.lockout.repository.inmemory.InMemoryLockoutRepositoryConfig;

@RequiredArgsConstructor
public class LockoutConfigBuilder {

    private final LockoutRepositoryConfig repositoryConfig = new InMemoryLockoutRepositoryConfig();

    private final IdentityConfig identityConfig;

    public LockoutConfigBuilder() {
        this(new IdentityConfigBuilder().build());
    }

    public LockoutConfig build() {
        return LockoutConfig.builder()
                .attemptRepository(repositoryConfig.attemptRepository())
                .policyRepository(repositoryConfig.policyRepository())
                .findIdentityProvider(identityConfig)
                .aliasFactory(identityConfig.aliasFactory())
                .build();
    }

}
