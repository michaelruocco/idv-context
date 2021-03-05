package uk.co.idv.app.plain.context;

import org.junit.jupiter.api.Test;
import uk.co.idv.app.plain.Application;
import uk.co.idv.app.plain.TestHarness;
import uk.co.idv.context.entities.context.Context;
import uk.co.idv.context.entities.context.NotNextMethodException;
import uk.co.idv.context.entities.context.create.CreateContextRequest;
import uk.co.idv.context.entities.context.create.FacadeCreateContextRequestMother;
import uk.co.idv.method.entities.method.Methods;
import uk.co.idv.method.entities.verification.CreateVerificationRequest;
import uk.co.idv.method.entities.verification.CreateVerificationRequestMother;
import uk.co.idv.method.entities.verification.GetVerificationRequest;
import uk.co.idv.method.entities.verification.Verification;
import uk.co.idv.identity.entities.identity.Identity;
import uk.co.idv.lockout.entities.DefaultExternalLockoutRequest;
import uk.co.idv.lockout.entities.ExternalLockoutRequest;
import uk.co.idv.lockout.entities.LockedOutException;
import uk.co.idv.lockout.entities.attempt.Attempt;
import uk.co.idv.lockout.entities.policy.LockoutPolicy;
import uk.co.idv.lockout.entities.policy.LockoutPolicyMother;
import uk.co.idv.lockout.entities.policy.LockoutState;
import uk.co.idv.lockout.entities.policy.hard.HardLockoutStateCalculatorMother;
import uk.co.idv.lockout.entities.policy.recordattempt.NeverRecordAttemptPolicy;
import uk.co.idv.lockout.entities.policy.recordattempt.RecordAttemptWhenMethodCompletePolicy;
import uk.co.idv.lockout.entities.policy.soft.SoftLockoutStateCalculatorMother;
import uk.co.idv.method.entities.result.Result;
import uk.co.idv.policy.entities.policy.key.channel.ChannelPolicyKeyMother;

import java.time.Duration;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.Assertions.catchThrowableOfType;

class ContextVerificationIntegrationTest {

    private final TestHarness harness = new TestHarness();
    private final Application application = harness.getApplication();

    @Test
    void shouldPopulateVerificationOnContext() {
        CreateContextRequest createRequest = FacadeCreateContextRequestMother.build();
        harness.givenContextPolicyExistsForChannel(createRequest.getChannelId());
        harness.givenIdentityExistsForAliases(createRequest.getAliases());
        harness.givenHardLockoutPolicyExistsForChannel(createRequest.getChannelId());
        Context context = application.create(createRequest);

        CreateVerificationRequest verificationRequest = CreateVerificationRequestMother.builder()
                .contextId(context.getId())
                .methodName("fake-method")
                .build();
        Verification verification = application.create(verificationRequest);

        Context updated = application.findContext(context.getId());
        assertThat(updated.getVerification(verification.getId())).isEqualTo(verification);
    }

    @Test
    void shouldCreateVerificationWithCorrectFields() {
        CreateContextRequest createRequest = FacadeCreateContextRequestMother.build();
        harness.givenContextPolicyExistsForChannel(createRequest.getChannelId());
        harness.givenIdentityExistsForAliases(createRequest.getAliases());
        harness.givenHardLockoutPolicyExistsForChannel(createRequest.getChannelId());
        Context context = application.create(createRequest);

        CreateVerificationRequest verificationRequest = CreateVerificationRequestMother.builder()
                .contextId(context.getId())
                .methodName("fake-method")
                .build();
        Verification verification = application.create(verificationRequest);

        Methods expectedMethods = context.getNextMethods(verificationRequest.getMethodName());
        assertThat(verification.getId()).isEqualTo(UUID.fromString("507cc493-6998-49a4-9614-38ba4296eab6"));
        assertThat(verification.getContextId()).isEqualTo(context.getId());
        assertThat(verification.getCreated()).isEqualTo(context.getCreated());
        assertThat(verification.getMethodName()).isEqualTo(verificationRequest.getMethodName());
        assertThat(verification.getMethods()).isEqualTo(expectedMethods);
        assertThat(verification.getExpiry()).isEqualTo(context.getCreated().plus(expectedMethods.getShortestDuration()));
        assertThat(verification.isSuccessful()).isFalse();
        assertThat(verification.isComplete()).isFalse();
        assertThat(verification.getCompleted()).isEmpty();
    }

    @Test
    void shouldGetCreatedVerification() {
        CreateContextRequest createRequest = FacadeCreateContextRequestMother.build();
        harness.givenContextPolicyExistsForChannel(createRequest.getChannelId());
        harness.givenIdentityExistsForAliases(createRequest.getAliases());
        harness.givenHardLockoutPolicyExistsForChannel(createRequest.getChannelId());
        Context context = application.create(createRequest);

        CreateVerificationRequest createVerificationRequest = CreateVerificationRequestMother.builder()
                .contextId(context.getId())
                .methodName("fake-method")
                .build();
        Verification createdVerification = application.create(createVerificationRequest);

        GetVerificationRequest getVerificationRequest = GetVerificationRequest.builder()
                .contextId(context.getId())
                .verificationId(createdVerification.getId())
                .build();
        Verification verification = application.get(getVerificationRequest);
        assertThat(verification).isEqualTo(createdVerification);
    }

    @Test
    void shouldThrowExceptionIfVerificationMethodIsNotOnContext() {
        CreateContextRequest createRequest = FacadeCreateContextRequestMother.build();
        harness.givenContextPolicyExistsForChannel(createRequest.getChannelId());
        harness.givenIdentityExistsForAliases(createRequest.getAliases());
        harness.givenHardLockoutPolicyExistsForChannel(createRequest.getChannelId());
        Context context = application.create(createRequest);

        CreateVerificationRequest verificationRequest = CreateVerificationRequestMother.builder()
                .contextId(context.getId())
                .methodName("another-method")
                .build();
        Throwable error = catchThrowable(() -> application.create(verificationRequest));

        assertThat(error)
                .isInstanceOf(NotNextMethodException.class)
                .hasMessage(verificationRequest.getMethodName());
    }

    @Test
    void shouldThrowExceptionIfAttemptToCreateVerificationOnContextForMethodThatIsAlreadyComplete() {
        CreateContextRequest createRequest = FacadeCreateContextRequestMother.build();
        harness.givenContextPolicyExistsForChannel(createRequest.getChannelId());
        harness.givenIdentityExistsForAliases(createRequest.getAliases());
        harness.givenHardLockoutPolicyExistsForChannel(createRequest.getChannelId());
        Context context = application.create(createRequest);
        CreateVerificationRequest verificationRequest = CreateVerificationRequestMother.builder()
                .contextId(context.getId())
                .methodName("fake-method")
                .build();
        harness.givenVerificationCompletedSuccessfully(verificationRequest);

        Throwable error = catchThrowable(() -> application.create(verificationRequest));

        assertThat(error)
                .isInstanceOf(NotNextMethodException.class)
                .hasMessage(verificationRequest.getMethodName());
    }

    @Test
    void shouldRecordLockoutAttemptsWhenVerificationsCompletedUnsuccessfully() {
        CreateContextRequest createRequest = FacadeCreateContextRequestMother.build();
        harness.givenContextPolicyExistsForChannel(createRequest.getChannelId());
        harness.givenIdentityExistsForAliases(createRequest.getAliases());
        harness.givenHardLockoutPolicyExistsForChannel(createRequest.getChannelId());
        Context context = application.create(createRequest);

        CreateVerificationRequest verificationRequest = CreateVerificationRequest.builder()
                .contextId(context.getId())
                .methodName("fake-method")
                .build();
        harness.givenVerificationCompletedUnsuccessfully(verificationRequest);
        harness.givenVerificationCompletedUnsuccessfully(verificationRequest);

        ExternalLockoutRequest lockoutRequest = DefaultExternalLockoutRequest.builder()
                .activityName(createRequest.getActivityName())
                .aliases(createRequest.getAliases())
                .channelId(createRequest.getChannelId())
                .build();
        LockoutState state = application.loadLockoutState(lockoutRequest);

        assertThat(state.getAttempts()).hasSize(2);
    }

    @Test
    void shouldRecordLockoutAttemptsWithCorrectValuesWhenVerificationCompletedUnsuccessfully() {
        CreateContextRequest createRequest = FacadeCreateContextRequestMother.build();
        harness.givenContextPolicyExistsForChannel(createRequest.getChannelId());
        harness.givenIdentityExistsForAliases(createRequest.getAliases());
        harness.givenHardLockoutPolicyExistsForChannel(createRequest.getChannelId());
        Context context = application.create(createRequest);

        CreateVerificationRequest verificationRequest = CreateVerificationRequest.builder()
                .contextId(context.getId())
                .methodName("fake-method")
                .build();
        Verification verification = harness.givenVerificationCompletedUnsuccessfully(verificationRequest);

        ExternalLockoutRequest lockoutRequest = DefaultExternalLockoutRequest.builder()
                .activityName(createRequest.getActivityName())
                .aliases(createRequest.getAliases())
                .channelId(createRequest.getChannelId())
                .build();
        LockoutState state = application.loadLockoutState(lockoutRequest);

        Result result = verification.toResult();
        Attempt attempt = state.attemptsCollection().iterator().next();
        assertThat(attempt.getVerificationId()).isEqualTo(result.getVerificationId());
        assertThat(attempt.getTimestamp()).isEqualTo(result.getTimestamp());
        assertThat(attempt.getMethodName()).isEqualTo(result.getMethodName());
        assertThat(attempt.isSuccessful()).isEqualTo(result.isSuccessful());
        assertThat(attempt.getContextId()).isEqualTo(context.getId());
        assertThat(attempt.getChannelId()).isEqualTo(context.getChannelId());
        assertThat(attempt.getIdvId()).isEqualTo(context.getIdvId());
        assertThat(attempt.getAliases()).isEqualTo(context.getAliases());
        assertThat(attempt.getActivityName()).isEqualTo(context.getActivityName());
    }

    @Test
    void shouldThrowExceptionWhenCreateVerificationAndIdentityIsLocked() {
        CreateContextRequest createRequest = FacadeCreateContextRequestMother.build();
        harness.givenContextPolicyExistsForChannel(createRequest.getChannelId());
        Identity identity = harness.givenIdentityExistsForAliases(createRequest.getAliases());
        harness.givenHardLockoutPolicyExistsForChannel(createRequest.getChannelId());
        Context context = application.create(createRequest);

        CreateVerificationRequest verificationRequest = CreateVerificationRequest.builder()
                .contextId(context.getId())
                .methodName("fake-method")
                .build();
        harness.givenVerificationCompletedUnsuccessfully(verificationRequest);
        harness.givenVerificationCompletedUnsuccessfully(verificationRequest);
        harness.givenVerificationCompletedUnsuccessfully(verificationRequest);

        Throwable error = catchThrowable(() -> application.create(verificationRequest));

        assertThat(error)
                .isInstanceOf(LockedOutException.class)
                .hasMessage(identity.getIdvId().format());
    }

    @Test
    void shouldNotRecordAttemptIfPolicyIsRecordOnMethodCompleteAndMethodIsNotComplete() {
        CreateContextRequest createRequest = FacadeCreateContextRequestMother.build();
        harness.givenContextPolicyExistsForChannel(createRequest.getChannelId());
        harness.givenIdentityExistsForAliases(createRequest.getAliases());
        LockoutPolicy lockoutPolicy = LockoutPolicyMother.builder()
                .key(ChannelPolicyKeyMother.withChannelId(createRequest.getChannelId()))
                .recordAttemptPolicy(new RecordAttemptWhenMethodCompletePolicy())
                .build();
        harness.givenLockoutPolicyExists(lockoutPolicy);
        Context context = application.create(createRequest);

        CreateVerificationRequest verificationRequest = CreateVerificationRequest.builder()
                .contextId(context.getId())
                .methodName("fake-method")
                .build();
        harness.givenVerificationCompletedUnsuccessfully(verificationRequest);

        ExternalLockoutRequest lockoutRequest = DefaultExternalLockoutRequest.builder()
                .activityName(createRequest.getActivityName())
                .aliases(createRequest.getAliases())
                .channelId(createRequest.getChannelId())
                .build();
        LockoutState state = application.loadLockoutState(lockoutRequest);

        assertThat(state.getAttempts()).isEmpty();
    }

    @Test
    void shouldNotRecordAttemptIfPolicyIsNeverRecord() {
        CreateContextRequest createRequest = FacadeCreateContextRequestMother.build();
        harness.givenContextPolicyExistsForChannel(createRequest.getChannelId());
        harness.givenIdentityExistsForAliases(createRequest.getAliases());
        LockoutPolicy lockoutPolicy = LockoutPolicyMother.builder()
                .key(ChannelPolicyKeyMother.withChannelId(createRequest.getChannelId()))
                .recordAttemptPolicy(new NeverRecordAttemptPolicy())
                .build();
        harness.givenLockoutPolicyExists(lockoutPolicy);
        Context context = application.create(createRequest);

        CreateVerificationRequest verificationRequest = CreateVerificationRequest.builder()
                .contextId(context.getId())
                .methodName("fake-method")
                .build();
        harness.givenVerificationCompletedUnsuccessfully(verificationRequest);

        ExternalLockoutRequest lockoutRequest = DefaultExternalLockoutRequest.builder()
                .activityName(createRequest.getActivityName())
                .aliases(createRequest.getAliases())
                .channelId(createRequest.getChannelId())
                .build();
        LockoutState state = application.loadLockoutState(lockoutRequest);

        assertThat(state.getAttempts()).isEmpty();
    }

    @Test
    void shouldBeLockedIfHardLockoutPolicyConfiguredAndMaxAttemptsReached() {
        CreateContextRequest createRequest = FacadeCreateContextRequestMother.build();
        harness.givenContextPolicyExistsForChannel(createRequest.getChannelId());
        harness.givenIdentityExistsForAliases(createRequest.getAliases());
        LockoutPolicy lockoutPolicy = LockoutPolicyMother.builder()
                .key(ChannelPolicyKeyMother.withChannelId(createRequest.getChannelId()))
                .stateCalculator(HardLockoutStateCalculatorMother.withMaxNumberOfAttempts(2))
                .build();
        harness.givenLockoutPolicyExists(lockoutPolicy);
        Context context = application.create(createRequest);

        CreateVerificationRequest verificationRequest = CreateVerificationRequest.builder()
                .contextId(context.getId())
                .methodName("fake-method")
                .build();
        harness.givenVerificationCompletedUnsuccessfully(verificationRequest);
        harness.givenVerificationCompletedUnsuccessfully(verificationRequest);

        LockedOutException error = catchThrowableOfType(
                () -> application.create(verificationRequest),
                LockedOutException.class
        );

        LockoutState state = error.getState();
        assertThat(state.isLocked()).isTrue();
        assertThat(state.getMessage()).isEqualTo("maximum number of attempts [2] reached");
    }

    @Test
    void shouldLockShouldExpireIfIncludeAttemptsWithinDurationFilterAppliedToPolicyAndDurationHasPassed() {
        CreateContextRequest createRequest = FacadeCreateContextRequestMother.build();
        harness.givenContextPolicyExistsForChannel(createRequest.getChannelId());
        harness.givenIdentityExistsForAliases(createRequest.getAliases());
        harness.givenHardLockoutPolicyWithIncludeAttemptsWithin24HoursExistsForChannel(createRequest.getChannelId());
        Context context = application.create(createRequest);

        CreateVerificationRequest verificationRequest = CreateVerificationRequest.builder()
                .contextId(context.getId())
                .methodName("fake-method")
                .build();
        harness.givenVerificationCompletedUnsuccessfully(verificationRequest);
        harness.givenVerificationCompletedUnsuccessfully(verificationRequest);
        harness.givenVerificationCompletedUnsuccessfully(verificationRequest);

        ExternalLockoutRequest lockoutRequest = DefaultExternalLockoutRequest.builder()
                .activityName(createRequest.getActivityName())
                .aliases(createRequest.getAliases())
                .channelId(createRequest.getChannelId())
                .build();
        LockoutState initialState = application.loadLockoutState(lockoutRequest);

        assertThat(initialState.isLocked()).isTrue();

        harness.fastForwardTimeBy(Duration.ofHours(24).plusMillis(1));

        LockoutState stateAfter24Hours = application.loadLockoutState(lockoutRequest);
        assertThat(stateAfter24Hours.isLocked()).isFalse();
    }

    @Test
    void shouldBeLockedIfSoftLockoutPolicyConfiguredAndBoundaryAttemptsReached() {
        CreateContextRequest createRequest = FacadeCreateContextRequestMother.build();
        harness.givenContextPolicyExistsForChannel(createRequest.getChannelId());
        harness.givenIdentityExistsForAliases(createRequest.getAliases());
        LockoutPolicy lockoutPolicy = LockoutPolicyMother.builder()
                .key(ChannelPolicyKeyMother.withChannelId(createRequest.getChannelId()))
                .stateCalculator(SoftLockoutStateCalculatorMother.build())
                .build();
        harness.givenLockoutPolicyExists(lockoutPolicy);
        Context context = application.create(createRequest);

        CreateVerificationRequest verificationRequest = CreateVerificationRequest.builder()
                .contextId(context.getId())
                .methodName("fake-method")
                .build();
        harness.givenVerificationCompletedUnsuccessfully(verificationRequest);

        System.out.println("completed unsuccessful verification, creating another verification");

        LockedOutException error = catchThrowableOfType(
                () -> application.create(verificationRequest),
                LockedOutException.class
        );

        LockoutState state = error.getState();
        assertThat(state.isLocked()).isTrue();
        assertThat(state.getMessage()).isEqualTo("soft lock began at 2020-10-06T21:00:00Z and expiring at 2020-10-06T21:01:00Z");
    }

    @Test
    void shouldResetLockoutStateOnVerificationCompletedSuccessfully() {
        CreateContextRequest createRequest = FacadeCreateContextRequestMother.build();
        harness.givenContextPolicyExistsForChannel(createRequest.getChannelId());
        harness.givenIdentityExistsForAliases(createRequest.getAliases());
        harness.givenHardLockoutPolicyExistsForChannel(createRequest.getChannelId());
        Context context = application.create(createRequest);

        CreateVerificationRequest verificationRequest = CreateVerificationRequest.builder()
                .contextId(context.getId())
                .methodName("fake-method")
                .build();
        harness.givenVerificationCompletedUnsuccessfully(verificationRequest);

        harness.givenVerificationCompletedSuccessfully(verificationRequest);

        ExternalLockoutRequest lockoutRequest = DefaultExternalLockoutRequest.builder()
                .activityName(createRequest.getActivityName())
                .aliases(createRequest.getAliases())
                .channelId(createRequest.getChannelId())
                .build();
        LockoutState state = application.loadLockoutState(lockoutRequest);

        assertThat(state.isLocked()).isFalse();
        assertThat(state.getAttempts()).isEmpty();
        assertThat(state.getMessage()).contains("");
    }

}
