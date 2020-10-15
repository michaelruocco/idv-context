package uk.co.idv.lockout.config;

import lombok.Builder;
import uk.co.idv.common.usecases.id.RandomIdGenerator;
import uk.co.idv.identity.usecases.identity.find.FindIdentity;
import uk.co.idv.lockout.adapter.json.error.handler.LockoutErrorHandler;
import uk.co.idv.identity.entities.alias.AliasFactory;
import uk.co.idv.lockout.usecases.ExternalLockoutRequestConverter;
import uk.co.idv.lockout.usecases.LockoutFacade;
import uk.co.idv.lockout.usecases.LockoutService;
import uk.co.idv.lockout.usecases.attempt.AttemptRepository;
import uk.co.idv.lockout.usecases.attempt.LoadAttempts;
import uk.co.idv.lockout.usecases.attempt.SaveAttempts;
import uk.co.idv.lockout.usecases.policy.LockoutPoliciesPopulator;
import uk.co.idv.lockout.usecases.policy.LockoutPolicyRepository;
import uk.co.idv.lockout.usecases.policy.LockoutPolicyService;
import uk.co.idv.lockout.usecases.state.LoadLockoutState;
import uk.co.idv.lockout.usecases.state.RecordAttempt;
import uk.co.idv.lockout.usecases.state.ResetLockoutState;
import uk.co.idv.lockout.usecases.state.ValidateLockoutState;

import java.time.Clock;

@Builder
public class LockoutConfig {

    private final LockoutPolicyRepository policyRepository;
    private final AttemptRepository attemptRepository;
    private final FindIdentity findIdentity;
    private final AliasFactory aliasFactory;

    public LockoutFacade getFacade() {
        return LockoutFacade.builder()
                .lockoutService(lockoutService())
                .converter(lockoutRequestConverter())
                .findIdentity(findIdentity)
                .aliasFactory(aliasFactory)
                .build();
    }

    public LockoutService lockoutService() {
        return LockoutService.builder()
                .recordAttempt(recordAttempt())
                .load(loadState())
                .reset(resetState())
                .validate(new ValidateLockoutState())
                .build();
    }

    public void populatePolicies(LockoutPoliciesProvider policiesProvider) {
        LockoutPoliciesPopulator populator = new LockoutPoliciesPopulator(getPolicyService());
        populator.populate(policiesProvider);
    }

    public LockoutPolicyService getPolicyService() {
        return new LockoutPolicyService(policyRepository);
    }

    public LockoutErrorHandler getErrorHandler() {
        return new LockoutErrorHandler();
    }

    private ExternalLockoutRequestConverter lockoutRequestConverter() {
        return new ExternalLockoutRequestConverter(Clock.systemUTC());
    }

    private RecordAttempt recordAttempt() {
        return RecordAttempt.builder()
                .policyService(getPolicyService())
                .reset(resetState())
                .save(saveAttempts())
                .load(loadState())
                .build();
    }

    private ResetLockoutState resetState() {
        return ResetLockoutState.builder()
                .loadAttempts(loadAttempts())
                .policyService(getPolicyService())
                .saveAttempts(saveAttempts())
                .build();
    }

    private SaveAttempts saveAttempts() {
        return SaveAttempts.builder()
                .loadAttempts(loadAttempts())
                .repository(attemptRepository)
                .build();
    }

    private LoadLockoutState loadState() {
        return LoadLockoutState.builder()
                .loadAttempts(loadAttempts())
                .policyService(getPolicyService())
                .build();
    }

    private LoadAttempts loadAttempts() {
        return LoadAttempts.builder()
                .idGenerator(new RandomIdGenerator())
                .repository(attemptRepository)
                .build();
    }

}
