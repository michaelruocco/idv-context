package uk.co.idv.context.config.lockout;

import lombok.Builder;
import uk.co.idv.common.usecases.id.RandomIdGenerator;
import uk.co.idv.context.adapter.json.lockout.error.handler.LockoutErrorHandler;
import uk.co.idv.identity.config.FindIdentityProvider;
import uk.co.idv.identity.entities.alias.AliasFactory;
import uk.co.idv.context.usecases.lockout.ExternalLockoutRequestConverter;
import uk.co.idv.context.usecases.lockout.LockoutFacade;
import uk.co.idv.context.usecases.lockout.LockoutService;
import uk.co.idv.context.usecases.lockout.attempt.AttemptRepository;
import uk.co.idv.context.usecases.lockout.attempt.LoadAttempts;
import uk.co.idv.context.usecases.lockout.attempt.SaveAttempts;
import uk.co.idv.context.usecases.lockout.policy.LockoutPolicyRepository;
import uk.co.idv.context.usecases.lockout.policy.LockoutPolicyService;
import uk.co.idv.context.usecases.lockout.state.LoadLockoutState;
import uk.co.idv.context.usecases.lockout.state.RecordAttempt;
import uk.co.idv.context.usecases.lockout.state.ResetLockoutState;
import uk.co.idv.context.usecases.lockout.state.ValidateLockoutState;

import java.time.Clock;

@Builder
public class LockoutConfig {

    private final LockoutPolicyRepository policyRepository;
    private final AttemptRepository attemptRepository;
    private final FindIdentityProvider findIdentityProvider;
    private final AliasFactory aliasFactory;

    public LockoutFacade lockoutFacade() {
        return LockoutFacade.builder()
                .lockoutService(lockoutService())
                .converter(lockoutRequestConverter())
                .findIdentity(findIdentityProvider.provideFindIdentity())
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

    public LockoutPolicyService policyService() {
        return new LockoutPolicyService(policyRepository);
    }

    public LockoutErrorHandler errorHandler() {
        return new LockoutErrorHandler();
    }

    private ExternalLockoutRequestConverter lockoutRequestConverter() {
        return new ExternalLockoutRequestConverter(Clock.systemUTC());
    }

    private RecordAttempt recordAttempt() {
        return RecordAttempt.builder()
                .policyService(policyService())
                .reset(resetState())
                .save(saveAttempts())
                .load(loadState())
                .build();
    }

    private ResetLockoutState resetState() {
        return ResetLockoutState.builder()
                .loadAttempts(loadAttempts())
                .policyService(policyService())
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
                .policyService(policyService())
                .build();
    }

    private LoadAttempts loadAttempts() {
        return LoadAttempts.builder()
                .idGenerator(new RandomIdGenerator())
                .repository(attemptRepository)
                .build();
    }

}
