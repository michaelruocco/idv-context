package uk.co.idv.app.manual.context;

import org.junit.jupiter.api.Test;
import uk.co.idv.app.manual.Application;
import uk.co.idv.app.manual.TestHarness;
import uk.co.idv.context.entities.context.Context;
import uk.co.idv.context.entities.context.create.CreateContextRequest;
import uk.co.idv.context.entities.context.create.FacadeCreateContextRequestMother;
import uk.co.idv.context.entities.result.FacadeRecordResultRequest;
import uk.co.idv.context.entities.result.FacadeRecordResultRequestMother;
import uk.co.idv.context.usecases.context.result.NotNextMethodException;
import uk.co.idv.identity.entities.identity.Identity;
import uk.co.idv.lockout.entities.DefaultExternalLockoutRequest;
import uk.co.idv.lockout.entities.ExternalLockoutRequest;
import uk.co.idv.lockout.entities.attempt.Attempt;
import uk.co.idv.lockout.entities.policy.LockoutPolicy;
import uk.co.idv.lockout.entities.policy.LockoutPolicyMother;
import uk.co.idv.lockout.entities.policy.LockoutState;
import uk.co.idv.lockout.entities.policy.hard.HardLockoutStateCalculatorMother;
import uk.co.idv.lockout.entities.policy.recordattempt.NeverRecordAttemptPolicy;
import uk.co.idv.lockout.entities.policy.recordattempt.RecordAttemptWhenMethodCompletePolicy;
import uk.co.idv.lockout.entities.policy.soft.SoftLockoutStateCalculatorMother;
import uk.co.idv.lockout.usecases.state.LockedOutException;
import uk.co.idv.method.entities.result.Result;
import uk.co.idv.method.entities.result.ResultMother;
import uk.co.idv.policy.entities.policy.key.ChannelPolicyKeyMother;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.Assertions.catchThrowableOfType;

class ContextResultIntegrationTest {

    private final TestHarness harness = new TestHarness();
    private final Application application = harness.getApplication();

    @Test
    void shouldPopulateResultOnContext() {
        CreateContextRequest createRequest = FacadeCreateContextRequestMother.build();
        harness.givenContextPolicyExistsForChannel(createRequest.getChannelId());
        harness.givenIdentityExistsForAliases(createRequest.getAliases());
        harness.givenLockoutPolicyExistsForChannel(createRequest.getChannelId());
        Context context = application.create(createRequest);

        FacadeRecordResultRequest recordRequest = FacadeRecordResultRequestMother.builder()
                .contextId(context.getId())
                .result(ResultMother.withMethodName("fake-method"))
                .build();
        Context updated = application.record(recordRequest);

        assertThat(updated.isComplete()).isTrue();
    }

    @Test
    void shouldThrowExceptionIfResultMethodIsNotOnContext() {
        CreateContextRequest createRequest = FacadeCreateContextRequestMother.build();
        harness.givenContextPolicyExistsForChannel(createRequest.getChannelId());
        harness.givenIdentityExistsForAliases(createRequest.getAliases());
        harness.givenLockoutPolicyExistsForChannel(createRequest.getChannelId());
        Context context = application.create(createRequest);

        Result result = ResultMother.withMethodName("another-method");
        FacadeRecordResultRequest recordRequest = FacadeRecordResultRequestMother.builder()
                .contextId(context.getId())
                .result(result)
                .build();
        Throwable error = catchThrowable(() -> application.record(recordRequest));

        assertThat(error)
                .isInstanceOf(NotNextMethodException.class)
                .hasMessage(result.getMethodName());
    }

    @Test
    void shouldThrowExceptionIfAttemptToPopulateResultOnContextThatIsAlreadyComplete() {
        CreateContextRequest createRequest = FacadeCreateContextRequestMother.build();
        harness.givenContextPolicyExistsForChannel(createRequest.getChannelId());
        harness.givenIdentityExistsForAliases(createRequest.getAliases());
        harness.givenLockoutPolicyExistsForChannel(createRequest.getChannelId());
        Context context = application.create(createRequest);
        Result result = ResultMother.withMethodName("fake-method");
        FacadeRecordResultRequest recordRequest = FacadeRecordResultRequestMother.builder()
                .contextId(context.getId())
                .result(result)
                .build();
        application.record(recordRequest);

        Throwable error = catchThrowable(() -> application.record(recordRequest));

        assertThat(error)
                .isInstanceOf(NotNextMethodException.class)
                .hasMessage(result.getMethodName());
    }

    @Test
    void shouldRecordAttemptsWhenResultRecorded() {
        CreateContextRequest createRequest = FacadeCreateContextRequestMother.build();
        harness.givenContextPolicyExistsForChannel(createRequest.getChannelId());
        harness.givenIdentityExistsForAliases(createRequest.getAliases());
        harness.givenLockoutPolicyExistsForChannel(createRequest.getChannelId());
        Context context = application.create(createRequest);

        Result result = ResultMother.builder()
                .methodName("fake-method")
                .successful(false)
                .build();
        FacadeRecordResultRequest recordRequest = FacadeRecordResultRequestMother.builder()
                .contextId(context.getId())
                .result(result)
                .build();
        application.record(recordRequest);
        application.record(recordRequest);

        ExternalLockoutRequest lockoutRequest = DefaultExternalLockoutRequest.builder()
                .activityName(createRequest.getActivityName())
                .aliases(createRequest.getAliases())
                .channelId(createRequest.getChannelId())
                .build();
        LockoutState state = application.loadLockoutState(lockoutRequest);

        assertThat(state.getAttempts()).hasSize(2);
    }

    @Test
    void shouldRecordAttemptsWithCorrectValuesWhenAttemptRecorded() {
        CreateContextRequest createRequest = FacadeCreateContextRequestMother.build();
        harness.givenContextPolicyExistsForChannel(createRequest.getChannelId());
        harness.givenIdentityExistsForAliases(createRequest.getAliases());
        harness.givenLockoutPolicyExistsForChannel(createRequest.getChannelId());
        Context context = application.create(createRequest);

        Result result = ResultMother.builder()
                .methodName("fake-method")
                .successful(false)
                .build();
        FacadeRecordResultRequest recordRequest = FacadeRecordResultRequestMother.builder()
                .contextId(context.getId())
                .result(result)
                .build();
        application.record(recordRequest);

        ExternalLockoutRequest lockoutRequest = DefaultExternalLockoutRequest.builder()
                .activityName(createRequest.getActivityName())
                .aliases(createRequest.getAliases())
                .channelId(createRequest.getChannelId())
                .build();
        LockoutState state = application.loadLockoutState(lockoutRequest);

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
    void shouldThrowExceptionWhenRecordingAttemptWhenLocked() {
        CreateContextRequest createRequest = FacadeCreateContextRequestMother.build();
        harness.givenContextPolicyExistsForChannel(createRequest.getChannelId());
        Identity identity = harness.givenIdentityExistsForAliases(createRequest.getAliases());
        harness.givenLockoutPolicyExistsForChannel(createRequest.getChannelId());
        Context context = application.create(createRequest);

        Result result = ResultMother.builder()
                .methodName("fake-method")
                .successful(false)
                .build();
        FacadeRecordResultRequest recordRequest = FacadeRecordResultRequestMother.builder()
                .contextId(context.getId())
                .result(result)
                .build();
        application.record(recordRequest);
        application.record(recordRequest);
        application.record(recordRequest);

        Throwable error = catchThrowable(() -> application.record(recordRequest));

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

        Result result = ResultMother.builder()
                .methodName("fake-method")
                .successful(false)
                .build();
        FacadeRecordResultRequest recordRequest = FacadeRecordResultRequestMother.builder()
                .contextId(context.getId())
                .result(result)
                .build();
        application.record(recordRequest);

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

        Result result = ResultMother.builder()
                .methodName("fake-method")
                .successful(false)
                .build();
        FacadeRecordResultRequest recordRequest = FacadeRecordResultRequestMother.builder()
                .contextId(context.getId())
                .result(result)
                .build();
        application.record(recordRequest);

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

        Result result = ResultMother.builder()
                .methodName("fake-method")
                .successful(false)
                .build();
        FacadeRecordResultRequest recordRequest = FacadeRecordResultRequestMother.builder()
                .contextId(context.getId())
                .result(result)
                .build();
        application.record(recordRequest);
        application.record(recordRequest);
        LockedOutException error = catchThrowableOfType(
                () -> application.record(recordRequest),
                LockedOutException.class
        );

        LockoutState state = error.getState();
        assertThat(state.isLocked()).isTrue();
        assertThat(state.getMessage()).isEqualTo("maximum number of attempts [2] reached");
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

        Result result = ResultMother.builder()
                .methodName("fake-method")
                .successful(false)
                .build();
        FacadeRecordResultRequest recordRequest = FacadeRecordResultRequestMother.builder()
                .contextId(context.getId())
                .result(result)
                .build();
        application.record(recordRequest);
        LockedOutException error = catchThrowableOfType(
                () -> application.record(recordRequest),
                LockedOutException.class
        );

        LockoutState state = error.getState();
        assertThat(state.isLocked()).isTrue();
        assertThat(state.getMessage()).isEqualTo("soft lock began at 2020-10-01T19:10:22Z and expiring at 2020-10-01T19:11:22Z");
    }

    @Test
    void shouldResetLockoutStateOnSuccessfulAttempt() {
        CreateContextRequest createRequest = FacadeCreateContextRequestMother.build();
        harness.givenContextPolicyExistsForChannel(createRequest.getChannelId());
        harness.givenIdentityExistsForAliases(createRequest.getAliases());
        harness.givenLockoutPolicyExistsForChannel(createRequest.getChannelId());
        Context context = application.create(createRequest);

        Result result = ResultMother.builder()
                .methodName("fake-method")
                .successful(false)
                .build();
        FacadeRecordResultRequest recordUnsuccessfulRequest = FacadeRecordResultRequestMother.builder()
                .contextId(context.getId())
                .result(result)
                .build();
        application.record(recordUnsuccessfulRequest);
        FacadeRecordResultRequest recordSuccessfulRequest = FacadeRecordResultRequestMother.builder()
                .contextId(context.getId())
                .result(ResultMother.withMethodName("fake-method"))
                .build();
        application.record(recordSuccessfulRequest);

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
