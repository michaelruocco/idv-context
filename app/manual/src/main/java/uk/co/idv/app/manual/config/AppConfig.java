package uk.co.idv.app.manual.config;

import lombok.Data;
import uk.co.idv.app.manual.adapter.app.AppAdapter;
import uk.co.idv.common.adapter.json.error.handler.ErrorHandler;
import uk.co.idv.identity.adapter.eligibility.external.StubExternalFindIdentityConfig;
import uk.co.idv.identity.config.ExternalFindIdentityConfig;
import uk.co.idv.method.usecases.MethodBuilders;
import uk.co.idv.app.manual.adapter.repository.RepositoryAdapter;
import uk.co.idv.context.config.ContextConfig;
import uk.co.idv.identity.config.DefaultIdentityConfig;
import uk.co.idv.identity.config.IdentityConfig;
import uk.co.idv.lockout.config.LockoutConfig;

@Data
public class AppConfig {

    private final ExternalFindIdentityConfig externalFindIdentityConfig = StubExternalFindIdentityConfig.build();

    private final IdentityConfig identityConfig;
    private final LockoutConfig lockoutConfig;
    private final ContextConfig contextConfig;
    private final ErrorHandler errorHandler;

    public AppConfig(MethodBuilders methodBuilders,
                     RepositoryAdapter repositoryAdapter,
                     AppAdapter appAdapter) {
        this.identityConfig = identityConfig(repositoryAdapter, appAdapter);
        this.lockoutConfig = lockoutConfig(repositoryAdapter, appAdapter, identityConfig);
        this.contextConfig = contextConfig(repositoryAdapter, appAdapter, methodBuilders, identityConfig, lockoutConfig);
        this.errorHandler = appAdapter.getErrorHandler();
        identityConfig.addMergeIdentitiesHandler(lockoutConfig.getMergeIdentitiesHandler());
    }

    private IdentityConfig identityConfig(RepositoryAdapter repositoryAdapter,
                                          AppAdapter appAdapter) {
        return DefaultIdentityConfig.builder()
                .idGenerator(appAdapter.getIdGenerator())
                .repository(repositoryAdapter.getIdentityRepository())
                .externalFindIdentityConfig(externalFindIdentityConfig)
                .build();
    }

    private LockoutConfig lockoutConfig(RepositoryAdapter repositoryAdapter,
                                        AppAdapter appAdapter,
                                        IdentityConfig identityConfig) {
        return LockoutConfig.builder()
                .idGenerator(appAdapter.getIdGenerator())
                .attemptRepository(repositoryAdapter.getAttemptRepository())
                .policyRepository(repositoryAdapter.getLockoutPolicyRepository())
                .findIdentity(identityConfig.findIdentity())
                .build();
    }

    private ContextConfig contextConfig(RepositoryAdapter repositoryAdapter,
                                       AppAdapter appAdapter,
                                       MethodBuilders methodBuilders,
                                       IdentityConfig identityConfig,
                                       LockoutConfig lockoutConfig) {
        return ContextConfig.builder()
                .contextRepository(repositoryAdapter.getContextRepository())
                .policyRepository(repositoryAdapter.getContextPolicyRepository())
                .clock(appAdapter.getClock())
                .idGenerator(appAdapter.getIdGenerator())
                .methodBuilders(methodBuilders)
                .createEligibility(identityConfig.createEligibility())
                .lockoutService(lockoutConfig.lockoutService())
                .build();
    }

}
