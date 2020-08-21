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
import uk.co.idv.context.entities.lockout.policy.soft.RecurringSoftLockoutPolicyMother;
import uk.co.idv.context.usecases.identity.IdentityService;
import uk.co.idv.context.usecases.identity.find.IdentityNotFoundException;
import uk.co.idv.context.usecases.lockout.LockoutFacade;
import uk.co.idv.context.usecases.lockout.LockoutService;
import uk.co.idv.context.usecases.lockout.policy.LockoutPolicyService;
import uk.co.idv.context.usecases.lockout.state.LockedOutException;
import uk.co.idv.context.usecases.policy.NoPoliciesConfiguredForRequestException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.Assertions.catchThrowableOfType;
import static uk.co.idv.context.entities.lockout.attempt.AttemptMother.successful;
import static uk.co.idv.context.entities.lockout.attempt.AttemptMother.unsuccessful;

public class LockoutIntegrationTest {

    private final IdentityConfig identityConfig = new IdentityConfigBuilder().build();
    private final IdentityService identityService = identityConfig.identityService();

    private final LockoutConfig lockoutConfig = new LockoutConfigBuilder(identityConfig).build();
    private final LockoutPolicyService policyService = lockoutConfig.policyService();
    private final LockoutService lockoutService = lockoutConfig.lockoutService();
    private final LockoutFacade lockoutFacade = lockoutConfig.lockoutFacade();

    @Test
    void shouldThrowExceptionNoPoliciesConfiguredForAttempt() {
        RecordAttemptRequest request = DefaultRecordAttemptRequestMother.build();

        Throwable error = catchThrowable(() -> lockoutService.recordAttemptIfRequired(request));

        assertThat(error).isInstanceOf(NoPoliciesConfiguredForRequestException.class);
    }

    @Test
    void shouldRecordUnsuccessfulAttempt() {
        policyService.create(HardLockoutPolicyMother.build());
        Attempt attempt = unsuccessful();
        RecordAttemptRequest request = DefaultRecordAttemptRequestMother.withAttempt(attempt);

        LockoutState state = lockoutService.recordAttemptIfRequired(request);

        assertThat(state.getAttempts()).contains(request.getAttempt());
    }

    @Test
    void shouldBeLockedAfterMaxNumberOfAttemptsForHardLockoutPolicy() {
        policyService.create(HardLockoutPolicyMother.build());
        Attempt attempt = unsuccessful();
        RecordAttemptRequest request = DefaultRecordAttemptRequestMother.withAttempt(attempt);
        lockoutService.recordAttemptIfRequired(request);
        lockoutService.recordAttemptIfRequired(request);

        LockoutState state = lockoutService.recordAttemptIfRequired(request);

        assertThat(state.getAttempts()).containsExactly(attempt, attempt, attempt);
        assertThat(state.isLocked()).isTrue();
        assertThat(state.getMessage()).isEqualTo("maximum number of attempts [3] reached");
    }

    @Test
    void shouldThrowExceptionIfRecordAttemptAfterMaxNumberOfAttemptsForHardLockoutPolicy() {
        policyService.create(HardLockoutPolicyMother.build());
        Attempt attempt = unsuccessful();
        RecordAttemptRequest request = DefaultRecordAttemptRequestMother.withAttempt(attempt);
        lockoutService.recordAttemptIfRequired(request);
        lockoutService.recordAttemptIfRequired(request);
        lockoutService.recordAttemptIfRequired(request);

        LockedOutException error = catchThrowableOfType(
                () -> lockoutService.recordAttemptIfRequired(request),
                LockedOutException.class
        );

        LockoutState state = error.getState();
        assertThat(state.getAttempts()).containsExactly(attempt, attempt, attempt);
        assertThat(state.isLocked()).isTrue();
        assertThat(state.getMessage()).isEqualTo("maximum number of attempts [3] reached");
    }

    @Test
    void shouldBeLockedAfterNumberOfAttemptsForRecurringSoftLockoutPolicy() {
        policyService.create(RecurringSoftLockoutPolicyMother.build());
        Attempt attempt = unsuccessful();
        RecordAttemptRequest request = DefaultRecordAttemptRequestMother.withAttempt(attempt);

        LockoutState state = lockoutService.recordAttemptIfRequired(request);

        assertThat(state.getAttempts()).containsExactly(attempt);
        assertThat(state.isLocked()).isTrue();
        assertThat(state.getMessage()).isEqualTo("soft lock began at 2019-09-27T09:35:15.612Z and expiring at 2019-09-27T09:36:15.612Z");
    }

    @Test
    void shouldThrowExceptionIfRecordAttemptAfterNumberOfAttemptsForRecurringSoftLockoutPolicy() {
        policyService.create(RecurringSoftLockoutPolicyMother.build());
        Attempt attempt = unsuccessful();
        RecordAttemptRequest request = DefaultRecordAttemptRequestMother.withAttempt(attempt);
        lockoutService.recordAttemptIfRequired(request);

        LockedOutException error = catchThrowableOfType(
                () -> lockoutService.recordAttemptIfRequired(request),
                LockedOutException.class
        );

        LockoutState state = error.getState();
        assertThat(state.getAttempts()).containsExactly(attempt);
        assertThat(state.isLocked()).isTrue();
        assertThat(state.getMessage()).isEqualTo("soft lock began at 2019-09-27T09:35:15.612Z and expiring at 2019-09-27T09:36:15.612Z");
    }

    @Test
    void shouldLoadState() {
        policyService.create(HardLockoutPolicyMother.build());
        RecordAttemptRequest request = DefaultRecordAttemptRequestMother.withAttempt(unsuccessful());
        lockoutService.recordAttemptIfRequired(request);
        lockoutService.recordAttemptIfRequired(request);
        lockoutService.recordAttemptIfRequired(request);

        LockoutState state = lockoutService.loadState(request);

        assertThat(state.isLocked()).isTrue();
    }

    @Test
    void shouldResetState() {
        policyService.create(HardLockoutPolicyMother.build());
        RecordAttemptRequest request = DefaultRecordAttemptRequestMother.withAttempt(unsuccessful());
        lockoutService.recordAttemptIfRequired(request);
        lockoutService.recordAttemptIfRequired(request);

        LockoutState state = lockoutService.resetState(request);

        assertThat(state.isLocked()).isFalse();
        assertThat(state.getAttempts()).isEmpty();
        assertThat(state.getMessage()).isEqualTo("3 attempts remaining");
    }

    @Test
    void shouldThrowExceptionOnLoadAndValidateWhenLocked() {
        policyService.create(HardLockoutPolicyMother.build());
        RecordAttemptRequest request = DefaultRecordAttemptRequestMother.withAttempt(unsuccessful());
        lockoutService.recordAttemptIfRequired(request);
        lockoutService.recordAttemptIfRequired(request);
        lockoutService.recordAttemptIfRequired(request);

        LockedOutException error = catchThrowableOfType(
                () -> lockoutService.loadAndValidateState(request),
                LockedOutException.class
        );

        assertThat(error).isInstanceOf(LockedOutException.class);
        assertThat(error.getState().isLocked()).isTrue();
    }

    @Test
    void shouldResetFailedAttemptsOnSuccessfulAttempt() {
        policyService.create(HardLockoutPolicyMother.build());
        RecordAttemptRequest unsuccessfulRequest = DefaultRecordAttemptRequestMother.withAttempt(unsuccessful());
        lockoutService.recordAttemptIfRequired(unsuccessfulRequest);
        lockoutService.recordAttemptIfRequired(unsuccessfulRequest);
        RecordAttemptRequest successfulRequest = DefaultRecordAttemptRequestMother.withAttempt(successful());

        LockoutState state = lockoutService.recordAttemptIfRequired(successfulRequest);

        assertThat(state.getAttempts()).isEmpty();
        assertThat(state.isLocked()).isFalse();
    }

    @Test
    void shouldLoadLockoutStateFromExternalRequestIfIdentityExists() {
        policyService.create(HardLockoutPolicyMother.build());
        Attempt attempt = unsuccessful();
        Identity identity = IdentityMother.withAliases(attempt.getIdvId(), attempt.getAlias());
        identityService.update(identity);
        RecordAttemptRequest unsuccessfulRequest = DefaultRecordAttemptRequestMother.withAttempt(attempt);
        lockoutService.recordAttemptIfRequired(unsuccessfulRequest);
        ExternalLockoutRequest externalRequest = DefaultExternalLockoutRequestMother.withAlias(attempt.getAlias());

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
                .hasMessage(externalRequest.getAlias().format());
    }

    @Test
    void shouldThrowExceptionOnLoadStateIfNoPoliciesConfigured() {
        Identity identity = IdentityMother.example();
        identityService.update(identity);
        ExternalLockoutRequest externalRequest = DefaultExternalLockoutRequestMother.withAlias(identity.getIdvId());

        Throwable error = catchThrowable(() -> lockoutFacade.loadState(externalRequest));

        assertThat(error).isInstanceOf(NoPoliciesConfiguredForRequestException.class);
    }

    @Test
    void shouldResetLockoutStateStateFromExternalRequestIfIdentityExists() {
        policyService.create(HardLockoutPolicyMother.build());
        Attempt attempt = unsuccessful();
        Identity identity = IdentityMother.withAliases(attempt.getIdvId(), attempt.getAlias());
        identityService.update(identity);
        RecordAttemptRequest unsuccessfulRequest = DefaultRecordAttemptRequestMother.withAttempt(attempt);
        lockoutService.recordAttemptIfRequired(unsuccessfulRequest);
        ExternalLockoutRequest externalRequest = DefaultExternalLockoutRequestMother.withAlias(attempt.getAlias());

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
                .hasMessage(externalRequest.getAlias().format());
    }

    @Test
    void shouldThrowExceptionOnResetStateIfNoPoliciesConfigured() {
        Identity identity = IdentityMother.example();
        identityService.update(identity);
        ExternalLockoutRequest externalRequest = DefaultExternalLockoutRequestMother.withAlias(identity.getIdvId());

        Throwable error = catchThrowable(() -> lockoutFacade.resetState(externalRequest));

        assertThat(error).isInstanceOf(NoPoliciesConfiguredForRequestException.class);
    }

}
