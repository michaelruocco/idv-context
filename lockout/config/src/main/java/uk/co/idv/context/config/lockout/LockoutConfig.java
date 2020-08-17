package uk.co.idv.context.config.lockout;

import lombok.Builder;
import uk.co.idv.context.adapter.json.lockout.error.handler.LockoutErrorHandler;
import uk.co.idv.context.config.identity.FindIdentityProvider;
import uk.co.idv.context.usecases.common.RandomIdGenerator;
import uk.co.idv.context.usecases.lockout.ExternalLockoutRequestConverter;
import uk.co.idv.context.usecases.lockout.LockoutFacade;
import uk.co.idv.context.usecases.lockout.LockoutService;
import uk.co.idv.context.usecases.lockout.attempt.AttemptsRepository;
import uk.co.idv.context.usecases.lockout.attempt.LoadAttempts;
import uk.co.idv.context.usecases.lockout.attempt.SaveAttempt;
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
    private final AttemptsRepository attemptsRepository;
    private final FindIdentityProvider findIdentityProvider;

    public LockoutFacade lockoutFacade() {
        return LockoutFacade.builder()
                .lockoutService(lockoutService())
                .converter(lockoutRequestConverter())
                .findIdentity(findIdentityProvider.provideFindIdentity())
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
                .save(saveAttempt())
                .load(loadState())
                .build();
    }

    private ResetLockoutState resetState() {
        return ResetLockoutState.builder()
                .loadAttempts(loadAttempts())
                .policyService(policyService())
                .repository(attemptsRepository)
                .build();
    }

    private SaveAttempt saveAttempt() {
        return SaveAttempt.builder()
                .loadAttempts(loadAttempts())
                .repository(attemptsRepository)
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
                .repository(attemptsRepository)
                .build();
    }

}
