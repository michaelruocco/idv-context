package uk.co.idv.app.manual.lockout;

import lombok.Builder;
import uk.co.idv.identity.config.DefaultIdentityConfig;
import uk.co.idv.identity.config.IdentityConfig;
import uk.co.idv.lockout.config.LockoutConfig;
import uk.co.idv.lockout.config.repository.LockoutRepositoryConfig;
import uk.co.idv.lockout.config.repository.inmemory.InMemoryLockoutRepositoryConfig;

@Builder
public class LockoutConfigBuilder {

    @Builder.Default
    private final IdentityConfig identityConfig = DefaultIdentityConfig.builder().build();

    @Builder.Default
    private final LockoutRepositoryConfig repositoryConfig = new InMemoryLockoutRepositoryConfig();

    public LockoutConfig build() {
        return LockoutConfig.builder()
                .findIdentity(identityConfig.findIdentity())
                .aliasFactory(identityConfig.aliasFactory())
                .attemptRepository(repositoryConfig.attemptRepository())
                .policyRepository(repositoryConfig.policyRepository())
                .build();
    }

}
