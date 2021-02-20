package uk.co.idv.app.plain.config;

import lombok.Data;
import uk.co.idv.app.plain.adapter.app.AppAdapter;
import uk.co.idv.common.adapter.json.error.handler.ErrorHandler;
import uk.co.idv.context.config.VerificationConfig;
import uk.co.idv.identity.config.ExternalFindIdentityConfig;
import uk.co.idv.method.config.AppMethodConfigs;
import uk.co.idv.app.plain.adapter.repository.RepositoryAdapter;
import uk.co.idv.context.config.ContextConfig;
import uk.co.idv.identity.config.DefaultIdentityConfig;
import uk.co.idv.identity.config.IdentityConfig;
import uk.co.idv.lockout.config.LockoutConfig;

@Data
public class AppConfig {

    private final ExternalFindIdentityConfig externalFindIdentityConfig;
    private final IdentityConfig identityConfig;
    private final LockoutConfig lockoutConfig;
    private final ContextConfig contextConfig;
    private final VerificationConfig verificationConfig;
    private final ErrorHandler errorHandler;

    public AppConfig(AppMethodConfigs appMethodConfigs,
                     RepositoryAdapter repositoryAdapter,
                     AppAdapter appAdapter,
                     ExternalFindIdentityConfig externalFindIdentityConfig) {
        this.externalFindIdentityConfig = externalFindIdentityConfig;
        this.identityConfig = identityConfig(repositoryAdapter, appAdapter);
        this.lockoutConfig = lockoutConfig(repositoryAdapter, appAdapter, identityConfig);
        this.contextConfig = contextConfig(repositoryAdapter, appAdapter, appMethodConfigs, identityConfig, lockoutConfig);
        this.verificationConfig = verificationConfig(repositoryAdapter, appAdapter, contextConfig);
        this.errorHandler = appAdapter.getErrorHandler();
        identityConfig.addMergeIdentitiesHandler(lockoutConfig.getMergeIdentitiesHandler());
    }

    private VerificationConfig verificationConfig(RepositoryAdapter repositoryAdapter, AppAdapter appAdapter, ContextConfig contextConfig) {
        return VerificationConfig.builder()
                .contextRepository(repositoryAdapter.getContextRepository())
                .clock(appAdapter.getClock())
                .uuidGenerator(appAdapter.getUuidGenerator())
                .contextConfig(contextConfig)
                .build();
    }

    private IdentityConfig identityConfig(RepositoryAdapter repositoryAdapter,
                                          AppAdapter appAdapter) {
        return DefaultIdentityConfig.builder()
                .uuidGenerator(appAdapter.getUuidGenerator())
                .repository(repositoryAdapter.getIdentityRepository())
                .externalFindIdentityConfig(externalFindIdentityConfig)
                .build();
    }

    private LockoutConfig lockoutConfig(RepositoryAdapter repositoryAdapter,
                                        AppAdapter appAdapter,
                                        IdentityConfig identityConfig) {
        return LockoutConfig.builder()
                .uuidGenerator(appAdapter.getUuidGenerator())
                .attemptRepository(repositoryAdapter.getAttemptRepository())
                .policyRepository(repositoryAdapter.getLockoutPolicyRepository())
                .findIdentity(identityConfig.findIdentity())
                .build();
    }

    private ContextConfig contextConfig(RepositoryAdapter repositoryAdapter,
                                        AppAdapter appAdapter,
                                        AppMethodConfigs appMethodConfigs,
                                        IdentityConfig identityConfig,
                                        LockoutConfig lockoutConfig) {
        return ContextConfig.builder()
                .contextRepository(repositoryAdapter.getContextRepository())
                .policyRepository(repositoryAdapter.getContextPolicyRepository())
                .clock(appAdapter.getClock())
                .uuidGenerator(appAdapter.getUuidGenerator())
                .appMethodConfigs(appMethodConfigs)
                .lockoutService(lockoutConfig.lockoutService())
                .identityConfig(identityConfig)
                .build();
    }

}
