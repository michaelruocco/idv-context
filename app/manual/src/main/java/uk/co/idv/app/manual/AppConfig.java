package uk.co.idv.app.manual;

import lombok.Data;
import uk.co.idv.app.manual.adapter.app.AppAdapter;
import uk.co.idv.method.usecases.MethodBuilders;
import uk.co.idv.app.manual.adapter.repository.RepositoryAdapter;
import uk.co.idv.context.config.ContextConfig;
import uk.co.idv.context.config.ContextPoliciesProvider;
import uk.co.idv.context.usecases.policy.ContextPoliciesPopulator;
import uk.co.idv.identity.config.DefaultIdentityConfig;
import uk.co.idv.identity.config.IdentityConfig;
import uk.co.idv.lockout.config.LockoutConfig;
import uk.co.idv.lockout.config.LockoutPoliciesProvider;
import uk.co.idv.lockout.usecases.policy.LockoutPoliciesPopulator;

@Data
public class AppConfig {

    private final IdentityConfig identityConfig;
    private final LockoutConfig lockoutConfig;
    private final ContextConfig contextConfig;

    public AppConfig(MethodBuilders methodBuilders,
                     RepositoryAdapter repositoryAdapter,
                     AppAdapter appAdapter) {
        this.identityConfig = identityConfig(repositoryAdapter);
        this.lockoutConfig = lockoutConfig(repositoryAdapter, identityConfig);
        this.contextConfig = contextConfig(repositoryAdapter, appAdapter, methodBuilders, identityConfig, lockoutConfig);
    }

    public void populatePolicies(ContextPoliciesProvider policiesProvider) {
        ContextPoliciesPopulator populator = new ContextPoliciesPopulator(contextConfig.getPolicyService());
        populator.populate(policiesProvider);
    }

    public void populatePolicies(LockoutPoliciesProvider policiesProvider) {
        LockoutPoliciesPopulator populator = new LockoutPoliciesPopulator(lockoutConfig.getPolicyService());
        populator.populate(policiesProvider);
    }

    private IdentityConfig identityConfig(RepositoryAdapter repositoryAdapter) {
        return DefaultIdentityConfig.builder()
                .repository(repositoryAdapter.getIdentityRepository())
                .build();
    }

    private LockoutConfig lockoutConfig(RepositoryAdapter repositoryAdapter,
                                        IdentityConfig identityConfig) {
        return LockoutConfig.builder()
                .attemptRepository(repositoryAdapter.getAttemptRepository())
                .policyRepository(repositoryAdapter.getLockoutPolicyRepository())
                .findIdentity(identityConfig.findIdentity())
                .aliasFactory(identityConfig.aliasFactory())
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
