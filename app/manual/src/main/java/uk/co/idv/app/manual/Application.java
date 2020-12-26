package uk.co.idv.app.manual;

import uk.co.idv.app.manual.adapter.channel.ChannelAdapter;
import uk.co.idv.app.manual.config.AppConfig;
import uk.co.idv.common.adapter.json.error.ApiError;
import uk.co.idv.common.adapter.json.error.handler.CompositeErrorHandler;
import uk.co.idv.common.adapter.json.error.handler.ErrorHandler;
import uk.co.idv.context.config.ContextConfig;
import uk.co.idv.context.entities.context.Context;
import uk.co.idv.context.entities.context.EligibleMethodsContext;
import uk.co.idv.context.entities.context.EligibleMethodsContextRequest;
import uk.co.idv.context.entities.context.create.CreateContextRequest;
import uk.co.idv.context.entities.policy.ContextPolicy;
import uk.co.idv.context.entities.result.FacadeRecordResultRequest;
import uk.co.idv.context.usecases.context.ContextFacade;
import uk.co.idv.context.usecases.policy.ContextPoliciesPopulator;
import uk.co.idv.context.usecases.policy.ContextPolicyService;
import uk.co.idv.identity.config.IdentityConfig;
import uk.co.idv.identity.entities.alias.Aliases;
import uk.co.idv.identity.entities.eligibility.IdentityEligibility;
import uk.co.idv.identity.entities.identity.FindIdentityRequest;
import uk.co.idv.identity.entities.identity.Identity;
import uk.co.idv.identity.usecases.eligibility.CreateEligibility;
import uk.co.idv.identity.usecases.identity.IdentityService;
import uk.co.idv.lockout.config.LockoutConfig;
import uk.co.idv.lockout.entities.ExternalLockoutRequest;
import uk.co.idv.lockout.entities.policy.LockoutPolicy;
import uk.co.idv.lockout.entities.policy.LockoutState;
import uk.co.idv.lockout.usecases.LockoutFacade;
import uk.co.idv.lockout.usecases.policy.LockoutPoliciesPopulator;
import uk.co.idv.lockout.usecases.policy.LockoutPolicyService;
import uk.co.idv.policy.entities.policy.Policies;
import uk.co.idv.policy.entities.policy.PolicyRequest;
import uk.co.idv.policy.entities.policy.key.PolicyKey;

import java.util.Optional;
import java.util.UUID;

public class Application {

    private ContextPolicyService contextPolicyService;
    private ContextFacade contextFacade;
    private ContextPoliciesPopulator contextPoliciesPopulator;

    private LockoutPolicyService lockoutPolicyService;
    private LockoutFacade lockoutFacade;
    private LockoutPoliciesPopulator lockoutPoliciesPopulator;

    private IdentityService identityService;
    private CreateEligibility createEligibility;

    private ErrorHandler errorHandler;

    public Application(AppConfig appConfig) {
        setUpContext(appConfig);
        setUpLockout(appConfig);
        setUpIdentity(appConfig);
        setUpErrorHandler(appConfig);
    }

    public void populatePolicies(ChannelAdapter channelAdapter) {
        contextPoliciesPopulator.populate(channelAdapter.getContextPolicies());
        lockoutPoliciesPopulator.populate(channelAdapter.getLockoutPolicies());
    }

    public void create(ContextPolicy policy) {
        contextPolicyService.create(policy);
    }

    public void update(ContextPolicy policy) {
        contextPolicyService.update(policy);
    }

    public void deleteContextPolicy(UUID id) {
        contextPolicyService.delete(id);
    }

    public ContextPolicy loadContextPolicy(UUID id) {
        return contextPolicyService.load(id);
    }

    public Policies<ContextPolicy> loadContextPolicies(PolicyRequest request) {
        return contextPolicyService.load(request);
    }

    public Policies<ContextPolicy> loadContextPolicies(PolicyKey key) {
        return contextPolicyService.load(key);
    }

    public Policies<ContextPolicy> loadAllContextPolicies() {
        return contextPolicyService.loadAll();
    }

    public void create(LockoutPolicy policy) {
        lockoutPolicyService.create(policy);
    }

    public void update(LockoutPolicy policy) {
        lockoutPolicyService.update(policy);
    }

    public void deleteLockoutPolicy(UUID id) {
        lockoutPolicyService.delete(id);
    }

    public LockoutPolicy loadLockoutPolicy(UUID id) {
        return lockoutPolicyService.load(id);
    }

    public Policies<LockoutPolicy> loadLockoutPolicies(PolicyRequest request) {
        return lockoutPolicyService.load(request);
    }

    public Policies<LockoutPolicy> loadLockoutPolicies(PolicyKey key) {
        return lockoutPolicyService.load(key);
    }

    public Policies<LockoutPolicy> loadAllLockoutPolicies() {
        return lockoutPolicyService.loadAll();
    }

    public Identity update(Identity identity) {
        return identityService.update(identity);
    }

    public Aliases toAliases(String type, String value) {
        return identityService.toAliases(type, value);
    }

    public Identity findIdentity(String aliasType, String aliasValue) {
        return identityService.find(aliasType, aliasValue);
    }

    public Identity findIdentity(UUID idvId) {
        return identityService.find(idvId);
    }

    public Identity findIdentity(Aliases aliases) {
        return identityService.find(aliases);
    }

    public IdentityEligibility create(FindIdentityRequest request) {
        return createEligibility.create(request);
    }

    public LockoutState loadLockoutState(ExternalLockoutRequest request) {
        return lockoutFacade.loadState(request);
    }

    public LockoutState resetLockoutState(ExternalLockoutRequest request) {
        return lockoutFacade.resetState(request);
    }

    public Context create(CreateContextRequest request) {
        return contextFacade.create(request);
    }

    public Context findContext(UUID id) {
        return contextFacade.find(id);
    }

    public EligibleMethodsContext findEligibleMethodsContext(EligibleMethodsContextRequest request) {
        return contextFacade.findEligibleMethodsContext(request);
    }

    public Context record(FacadeRecordResultRequest request) {
        return contextFacade.record(request);
    }

    public Optional<ApiError> handle(Throwable error) {
        return errorHandler.apply(error);
    }

    private void setUpContext(AppConfig appConfig) {
        ContextConfig contextConfig = appConfig.getContextConfig();
        this.contextPolicyService = contextConfig.policyService();
        this.contextFacade = contextConfig.getFacade();
        this.contextPoliciesPopulator = contextConfig.policiesPopulator();
    }

    private void setUpLockout(AppConfig appConfig) {
        LockoutConfig lockoutConfig = appConfig.getLockoutConfig();
        this.lockoutPolicyService = lockoutConfig.policyService();
        this.lockoutFacade = lockoutConfig.facade();
        this.lockoutPoliciesPopulator = lockoutConfig.policiesPopulator();
    }

    private void setUpIdentity(AppConfig appConfig) {
        IdentityConfig identityConfig = appConfig.getIdentityConfig();
        this.identityService = identityConfig.identityService();
        this.createEligibility = identityConfig.createEligibility();
    }

    private void setUpErrorHandler(AppConfig appConfig) {
        this.errorHandler = new CompositeErrorHandler(
                appConfig.getErrorHandler(),
                appConfig.getContextConfig().errorHandler(),
                appConfig.getLockoutConfig().errorHandler(),
                appConfig.getIdentityConfig().errorHandler()
        );
    }

}
