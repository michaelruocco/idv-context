package uk.co.idv.app.manual.lockout;

import org.junit.jupiter.api.Test;
import uk.co.idv.app.manual.identity.IdentityConfigBuilder;
import uk.co.idv.context.config.identity.IdentityConfig;
import uk.co.idv.context.config.lockout.LockoutConfig;
import uk.co.idv.context.entities.identity.Identity;
import uk.co.idv.context.entities.identity.IdentityMother;
import uk.co.idv.context.entities.lockout.DefaultExternalLockoutRequestMother;
import uk.co.idv.context.entities.lockout.DefaultRecordAttemptRequestMother;
import uk.co.idv.context.entities.lockout.ExternalLockoutRequest;
import uk.co.idv.context.entities.lockout.attempt.Attempt;
import uk.co.idv.context.entities.lockout.policy.LockoutState;
import uk.co.idv.context.entities.lockout.policy.RecordAttemptRequest;
import uk.co.idv.context.entities.lockout.policy.hard.HardLockoutPolicyMother;
import uk.co.idv.context.entities.lockout.policy.recordattempt.NeverRecordAttemptPolicy;
import uk.co.idv.context.entities.lockout.policy.recordattempt.RecordAttemptWhenMethodCompletePolicy;
import uk.co.idv.context.entities.lockout.policy.recordattempt.RecordAttemptWhenSequenceCompletePolicy;
import uk.co.idv.context.entities.lockout.policy.soft.RecurringSoftLockoutPolicyMother;
import uk.co.idv.context.usecases.identity.IdentityService;
import uk.co.idv.context.usecases.identity.find.IdentityNotFoundException;
import uk.co.idv.context.usecases.lockout.LockoutFacade;
import uk.co.idv.context.usecases.lockout.policy.LockoutPolicyService;
import uk.co.idv.context.usecases.lockout.policy.NoLockoutPoliciesConfiguredException;
import uk.co.idv.context.usecases.lockout.state.LockedOutException;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.Assertions.catchThrowableOfType;
import static uk.co.idv.context.entities.lockout.attempt.AttemptMother.successful;
import static uk.co.idv.context.entities.lockout.attempt.AttemptMother.unsuccessful;

public class LockoutIntegrationTest {

    private final IdentityConfig identityConfig = new IdentityConfigBuilder().build();
    private final IdentityService identityService = identityConfig.identityService();

    private final LockoutConfigBuilder lockoutConfigBuilder = LockoutConfigBuilder.builder()
            .identityConfig(identityConfig)
            .build();

    private final LockoutConfig lockoutConfig = lockoutConfigBuilder.build();
    private final LockoutPolicyService policyService = lockoutConfig.policyService();
    private final LockoutFacade lockoutFacade = lockoutConfig.lockoutFacade();

    @Test
    void shouldThrowExceptionIfIdentityDoesNotExistOnRecordAttempt() {
        RecordAttemptRequest request = DefaultRecordAttemptRequestMother.build();

        Throwable error = catchThrowable(() -> lockoutFacade.recordAttempt(request));

        assertThat(error).isInstanceOf(IdentityNotFoundException.class);
    }

    @Test
    void shouldThrowExceptionIfIdentityDoesNotExistOnLoadState() {
        ExternalLockoutRequest request = DefaultExternalLockoutRequestMother.build();

        Throwable error = catchThrowable(() -> lockoutFacade.loadState(request));

        assertThat(error).isInstanceOf(IdentityNotFoundException.class);
    }

    @Test
    void shouldThrowExceptionIfIdentityDoesNotExistOnResetState() {
        ExternalLockoutRequest request = DefaultExternalLockoutRequestMother.build();

        Throwable error = catchThrowable(() -> lockoutFacade.resetState(request));

        assertThat(error).isInstanceOf(IdentityNotFoundException.class);
    }

    @Test
    void shouldThrowExceptionNoPoliciesConfiguredForAttempt() {
        identityService.update(IdentityMother.example());
        RecordAttemptRequest request = DefaultRecordAttemptRequestMother.build();

        Throwable error = catchThrowable(() -> lockoutFacade.recordAttempt(request));

        assertThat(error).isInstanceOf(NoLockoutPoliciesConfiguredException.class);
    }

    @Test
    void shouldRecordUnsuccessfulAttempt() {
        identityService.update(IdentityMother.example());
        policyService.create(HardLockoutPolicyMother.build());
        RecordAttemptRequest request = DefaultRecordAttemptRequestMother.withAttempt(unsuccessful());

        LockoutState state = lockoutFacade.recordAttempt(request);

        assertThat(state.getAttempts()).contains(request.getAttempt());
    }

    @Test
    void shouldRecordUnsuccessfulAttemptIfPolicyRecordWhenMethodCompleteAndMethodComplete() {
        identityService.update(IdentityMother.example());
        policyService.create(HardLockoutPolicyMother.builder()
                .recordAttemptPolicy(new RecordAttemptWhenMethodCompletePolicy())
                .build());
        RecordAttemptRequest request = DefaultRecordAttemptRequestMother.builder()
                .attempt(unsuccessful())
                .methodComplete(true)
                .build();

        LockoutState state = lockoutFacade.recordAttempt(request);

        assertThat(state.getAttempts()).containsExactly(request.getAttempt());
    }

    @Test
    void shouldNotRecordUnsuccessfulAttemptIfPolicyRecordWhenMethodCompleteAndMethodNotComplete() {
        identityService.update(IdentityMother.example());
        policyService.create(HardLockoutPolicyMother.builder()
                .recordAttemptPolicy(new RecordAttemptWhenMethodCompletePolicy())
                .build());
        RecordAttemptRequest request = DefaultRecordAttemptRequestMother.builder()
                .attempt(unsuccessful())
                .methodComplete(false)
                .build();

        LockoutState state = lockoutFacade.recordAttempt(request);

        assertThat(state.getAttempts()).isEmpty();
    }

    @Test
    void shouldRecordUnsuccessfulAttemptIfPolicyRecordWhenSequenceCompleteAndSequenceComplete() {
        identityService.update(IdentityMother.example());
        policyService.create(HardLockoutPolicyMother.builder()
                .recordAttemptPolicy(new RecordAttemptWhenSequenceCompletePolicy())
                .build());
        RecordAttemptRequest request = DefaultRecordAttemptRequestMother.builder()
                .attempt(unsuccessful())
                .sequenceComplete(true)
                .build();

        LockoutState state = lockoutFacade.recordAttempt(request);

        assertThat(state.getAttempts()).containsExactly(request.getAttempt());
    }

    @Test
    void shouldNotRecordUnsuccessfulAttemptIfPolicyRecordWhenSequenceCompleteAndSequenceNotComplete() {
        identityService.update(IdentityMother.example());
        policyService.create(HardLockoutPolicyMother.builder()
                .recordAttemptPolicy(new RecordAttemptWhenSequenceCompletePolicy())
                .build());
        RecordAttemptRequest request = DefaultRecordAttemptRequestMother.builder()
                .attempt(unsuccessful())
                .sequenceComplete(false)
                .build();

        LockoutState state = lockoutFacade.recordAttempt(request);

        assertThat(state.getAttempts()).isEmpty();
    }

    @Test
    void shouldNotRecordUnsuccessfulAttemptIfPolicyNeverRecordAttempt() {
        identityService.update(IdentityMother.example());
        policyService.create(HardLockoutPolicyMother.builder()
                .recordAttemptPolicy(new NeverRecordAttemptPolicy())
                .build());
        RecordAttemptRequest request = DefaultRecordAttemptRequestMother.withAttempt(unsuccessful());

        LockoutState state = lockoutFacade.recordAttempt(request);

        assertThat(state.getAttempts()).isEmpty();
    }

    @Test
    void shouldBeLockedAfterMaxNumberOfAttemptsForHardLockoutPolicy() {
        identityService.update(IdentityMother.example());
        policyService.create(HardLockoutPolicyMother.build());
        Attempt attempt = unsuccessful();
        RecordAttemptRequest request = DefaultRecordAttemptRequestMother.withAttempt(attempt);
        lockoutFacade.recordAttempt(request);
        lockoutFacade.recordAttempt(request);

        LockoutState state = lockoutFacade.recordAttempt(request);

        assertThat(state.getAttempts()).containsExactly(attempt, attempt, attempt);
        assertThat(state.isLocked()).isTrue();
        assertThat(state.getMessage()).isEqualTo("maximum number of attempts [3] reached");
    }

    @Test
    void shouldThrowExceptionIfRecordAttemptAfterMaxNumberOfAttemptsForHardLockoutPolicy() {
        identityService.update(IdentityMother.example());
        policyService.create(HardLockoutPolicyMother.build());
        Attempt attempt = unsuccessful();
        RecordAttemptRequest request = DefaultRecordAttemptRequestMother.withAttempt(attempt);
        lockoutFacade.recordAttempt(request);
        lockoutFacade.recordAttempt(request);
        lockoutFacade.recordAttempt(request);

        LockedOutException error = catchThrowableOfType(
                () -> lockoutFacade.recordAttempt(request),
                LockedOutException.class
        );

        LockoutState state = error.getState();
        assertThat(state.getAttempts()).containsExactly(attempt, attempt, attempt);
        assertThat(state.isLocked()).isTrue();
        assertThat(state.getMessage()).isEqualTo("maximum number of attempts [3] reached");
    }

    @Test
    void shouldBeLockedAfterNumberOfAttemptsForRecurringSoftLockoutPolicy() {
        identityService.update(IdentityMother.example());
        policyService.create(RecurringSoftLockoutPolicyMother.build());
        Attempt attempt = unsuccessful();
        RecordAttemptRequest request = DefaultRecordAttemptRequestMother.withAttempt(attempt);

        LockoutState state = lockoutFacade.recordAttempt(request);

        assertThat(state.getAttempts()).containsExactly(attempt);
        assertThat(state.isLocked()).isTrue();
        assertThat(state.getMessage()).isEqualTo("soft lock began at 2019-09-27T09:35:15.612Z and expiring at 2019-09-27T09:36:15.612Z");
    }

    @Test
    void shouldThrowExceptionIfRecordAttemptAfterNumberOfAttemptsForRecurringSoftLockoutPolicy() {
        identityService.update(IdentityMother.example());
        policyService.create(RecurringSoftLockoutPolicyMother.build());
        Attempt attempt = unsuccessful();
        RecordAttemptRequest request = DefaultRecordAttemptRequestMother.withAttempt(attempt);
        lockoutFacade.recordAttempt(request);

        LockedOutException error = catchThrowableOfType(
                () -> lockoutFacade.recordAttempt(request),
                LockedOutException.class
        );

        LockoutState state = error.getState();
        assertThat(state.getAttempts()).containsExactly(attempt);
        assertThat(state.isLocked()).isTrue();
        assertThat(state.getMessage()).isEqualTo("soft lock began at 2019-09-27T09:35:15.612Z and expiring at 2019-09-27T09:36:15.612Z");
    }

    @Test
    void shouldBeAbleToRegisterAttemptsAgainAfterRecurringSoftLockHasExpired() {
        identityService.update(IdentityMother.example());
        policyService.create(RecurringSoftLockoutPolicyMother.build());
        Attempt unsuccessful = unsuccessful();
        lockoutFacade.recordAttempt(DefaultRecordAttemptRequestMother.withAttempt(unsuccessful));
        Attempt unsuccessfulAfterLockExpired = unsuccessful(Instant.parse("2019-09-27T09:37:15.612Z"));
        RecordAttemptRequest request = DefaultRecordAttemptRequestMother.withAttempt(unsuccessfulAfterLockExpired);

        LockoutState state = lockoutFacade.recordAttempt(request);

        assertThat(state.getAttempts()).containsExactly(unsuccessful, unsuccessfulAfterLockExpired);
        assertThat(state.isLocked()).isTrue();
        assertThat(state.getMessage()).isEqualTo("soft lock began at 2019-09-27T09:37:15.612Z and expiring at 2019-09-27T09:38:15.612Z");
    }

    @Test
    void shouldLoadState() {
        policyService.create(HardLockoutPolicyMother.build());
        Attempt attempt = unsuccessful();
        Identity identity = IdentityMother.withAliases(attempt.getAliases().add(attempt.getIdvId()));
        identityService.update(identity);
        RecordAttemptRequest request = DefaultRecordAttemptRequestMother.withAttempt(attempt);
        lockoutFacade.recordAttempt(request);
        lockoutFacade.recordAttempt(request);

        LockoutState state = lockoutFacade.loadState(request);

        assertThat(state.isLocked()).isFalse();
        assertThat(state.getAttempts()).containsExactly(attempt, attempt);
        assertThat(state.getMessage()).isEqualTo("1 attempts remaining");
    }

    @Test
    void shouldResetState() {
        policyService.create(HardLockoutPolicyMother.build());
        Attempt attempt = unsuccessful();
        Identity identity = IdentityMother.withAliases(attempt.getAliases().add(attempt.getIdvId()));
        identityService.update(identity);
        RecordAttemptRequest request = DefaultRecordAttemptRequestMother.withAttempt(attempt);
        lockoutFacade.recordAttempt(request);
        lockoutFacade.recordAttempt(request);

        LockoutState state = lockoutFacade.resetState(request);

        assertThat(state.isLocked()).isFalse();
        assertThat(state.getAttempts()).isEmpty();
        assertThat(state.getMessage()).isEqualTo("3 attempts remaining");
    }

    @Test
    void shouldThrowExceptionOnLoadStateWhenLocked() {
        policyService.create(HardLockoutPolicyMother.build());
        Attempt attempt = unsuccessful();
        Identity identity = IdentityMother.withAliases(attempt.getAliases().add(attempt.getIdvId()));
        identityService.update(identity);
        RecordAttemptRequest request = DefaultRecordAttemptRequestMother.withAttempt(unsuccessful());
        lockoutFacade.recordAttempt(request);
        lockoutFacade.recordAttempt(request);
        lockoutFacade.recordAttempt(request);

        LockedOutException error = catchThrowableOfType(
                () -> lockoutFacade.loadState(request),
                LockedOutException.class
        );

        assertThat(error).isInstanceOf(LockedOutException.class);
        assertThat(error.getState().isLocked()).isTrue();
    }

    @Test
    void shouldResetFailedAttemptsOnSuccessfulAttempt() {
        identityService.update(IdentityMother.example());
        policyService.create(HardLockoutPolicyMother.build());
        RecordAttemptRequest unsuccessfulRequest = DefaultRecordAttemptRequestMother.withAttempt(unsuccessful());
        lockoutFacade.recordAttempt(unsuccessfulRequest);
        lockoutFacade.recordAttempt(unsuccessfulRequest);
        RecordAttemptRequest successfulRequest = DefaultRecordAttemptRequestMother.withAttempt(successful());

        LockoutState state = lockoutFacade.recordAttempt(successfulRequest);

        assertThat(state.getAttempts()).isEmpty();
        assertThat(state.isLocked()).isFalse();
    }

    @Test
    void shouldLoadLockoutStateFromExternalRequestIfIdentityExists() {
        policyService.create(HardLockoutPolicyMother.build());
        Attempt attempt = unsuccessful();
        Identity identity = IdentityMother.withAliases(attempt.getAliases().add(attempt.getIdvId()));
        identityService.update(identity);
        RecordAttemptRequest unsuccessfulRequest = DefaultRecordAttemptRequestMother.withAttempt(attempt);
        lockoutFacade.recordAttempt(unsuccessfulRequest);
        ExternalLockoutRequest externalRequest = DefaultExternalLockoutRequestMother.withAliases(attempt.getAliases());

        LockoutState state = lockoutFacade.loadState(externalRequest);

        assertThat(state.getAttempts()).contains(attempt);
    }

    @Test
    void shouldThrowExceptionOnLoadStateForIdentityThatDoesNotExist() {
        policyService.create(HardLockoutPolicyMother.build());
        ExternalLockoutRequest externalRequest = DefaultExternalLockoutRequestMother.build();

        Throwable error = catchThrowable(() -> lockoutFacade.loadState(externalRequest));

        assertThat(error)
                .isInstanceOf(IdentityNotFoundException.class)
                .hasMessage(externalRequest.getAliases().format());
    }

    @Test
    void shouldThrowExceptionOnLoadStateIfNoPoliciesConfigured() {
        Identity identity = IdentityMother.example();
        identityService.update(identity);
        ExternalLockoutRequest externalRequest = DefaultExternalLockoutRequestMother.withAlias(identity.getIdvId());

        Throwable error = catchThrowable(() -> lockoutFacade.loadState(externalRequest));

        assertThat(error).isInstanceOf(NoLockoutPoliciesConfiguredException.class);
    }

    @Test
    void shouldResetLockoutStateStateFromExternalRequestIfIdentityExists() {
        policyService.create(HardLockoutPolicyMother.build());
        Attempt attempt = unsuccessful();
        Identity identity = IdentityMother.withAliases(attempt.getAliases().add(attempt.getIdvId()));
        identityService.update(identity);
        lockoutFacade.recordAttempt(DefaultRecordAttemptRequestMother.withAttempt(attempt));
        ExternalLockoutRequest externalRequest = DefaultExternalLockoutRequestMother.withAliases(attempt.getAliases());

        LockoutState state = lockoutFacade.resetState(externalRequest);

        assertThat(state.getAttempts()).isEmpty();
    }

    @Test
    void shouldThrowExceptionOnResetStateForIdentityThatDoesNotExist() {
        policyService.create(HardLockoutPolicyMother.build());
        ExternalLockoutRequest externalRequest = DefaultExternalLockoutRequestMother.build();

        Throwable error = catchThrowable(() -> lockoutFacade.resetState(externalRequest));

        assertThat(error)
                .isInstanceOf(IdentityNotFoundException.class)
                .hasMessage(externalRequest.getAliases().format());
    }

    @Test
    void shouldThrowExceptionOnResetStateIfNoPoliciesConfigured() {
        Identity identity = IdentityMother.example();
        identityService.update(identity);
        ExternalLockoutRequest externalRequest = DefaultExternalLockoutRequestMother.withAlias(identity.getIdvId());

        Throwable error = catchThrowable(() -> lockoutFacade.resetState(externalRequest));

        assertThat(error).isInstanceOf(NoLockoutPoliciesConfiguredException.class);
    }

}
