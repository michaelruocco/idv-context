package uk.co.idv.app.manual.context;

import org.junit.jupiter.api.Test;
import uk.co.idv.app.manual.AppConfig;
import uk.co.idv.app.manual.adapter.app.AppAdapter;
import uk.co.idv.app.manual.adapter.app.DefaultAppAdapter;
import uk.co.idv.app.manual.adapter.repository.InMemoryRepositoryAdapter;
import uk.co.idv.app.manual.adapter.repository.RepositoryAdapter;
import uk.co.idv.common.usecases.id.NonRandomIdGenerator;
import uk.co.idv.context.config.ContextConfig;
import uk.co.idv.context.entities.context.Context;
import uk.co.idv.context.usecases.context.result.NotNextMethodException;
import uk.co.idv.context.entities.context.create.CreateContextRequest;
import uk.co.idv.context.entities.context.create.FacadeCreateContextRequestMother;
import uk.co.idv.context.entities.policy.ContextPolicy;
import uk.co.idv.context.entities.policy.ContextPolicyMother;
import uk.co.idv.context.entities.policy.sequence.SequencePoliciesMother;
import uk.co.idv.context.entities.result.FacadeRecordResultRequest;
import uk.co.idv.context.entities.result.FacadeRecordResultRequestMother;
import uk.co.idv.context.usecases.context.ContextExpiredException;
import uk.co.idv.context.usecases.context.ContextFacade;
import uk.co.idv.context.usecases.context.ContextNotFoundException;
import uk.co.idv.context.usecases.policy.ContextPolicyService;
import uk.co.idv.context.usecases.policy.NoContextPoliciesConfiguredException;
import uk.co.idv.identity.entities.alias.Aliases;
import uk.co.idv.identity.entities.identity.Identity;
import uk.co.idv.identity.entities.identity.IdentityMother;
import uk.co.idv.identity.usecases.identity.IdentityService;
import uk.co.idv.identity.usecases.identity.find.IdentityNotFoundException;
import uk.co.idv.lockout.config.LockoutConfig;
import uk.co.idv.lockout.entities.DefaultExternalLockoutRequest;
import uk.co.idv.lockout.entities.ExternalLockoutRequest;
import uk.co.idv.lockout.entities.attempt.Attempt;
import uk.co.idv.lockout.entities.policy.AttemptsFilter;
import uk.co.idv.lockout.entities.policy.LockoutPolicy;
import uk.co.idv.lockout.entities.policy.LockoutPolicyMother;
import uk.co.idv.lockout.entities.policy.LockoutState;
import uk.co.idv.lockout.entities.policy.hard.HardLockoutStateCalculatorMother;
import uk.co.idv.lockout.entities.policy.recordattempt.NeverRecordAttemptPolicy;
import uk.co.idv.lockout.entities.policy.recordattempt.RecordAttemptWhenMethodCompletePolicy;
import uk.co.idv.lockout.entities.policy.soft.SoftLockoutStateCalculatorMother;
import uk.co.idv.lockout.usecases.LockoutFacade;
import uk.co.idv.lockout.usecases.policy.LockoutPolicyService;
import uk.co.idv.lockout.usecases.policy.NoLockoutPoliciesConfiguredException;
import uk.co.idv.lockout.usecases.state.LockedOutException;
import uk.co.idv.method.config.AppFakeMethodConfig;
import uk.co.idv.method.entities.method.fake.policy.FakeMethodPolicyMother;
import uk.co.idv.method.entities.result.Result;
import uk.co.idv.method.entities.result.ResultMother;
import uk.co.idv.method.usecases.MethodBuilders;
import uk.co.idv.policy.entities.policy.key.ChannelPolicyKeyMother;
import uk.co.idv.policy.entities.policy.key.PolicyKey;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.Assertions.catchThrowableOfType;

class ContextIntegrationTest {

    private static final Instant NOW = Instant.parse("2020-10-06T21:00:00.000Z");

    private final MethodBuilders methodBuilders = new MethodBuilders(new AppFakeMethodConfig().methodBuilder());
    private final RepositoryAdapter repositoryAdapter = new InMemoryRepositoryAdapter();
    private final UpdatableClock clock = new UpdatableClock(NOW);
    private final AppAdapter appAdapter = DefaultAppAdapter.builder()
            .repositoryAdapter(repositoryAdapter)
            .clock(clock)
            .idGenerator(new NonRandomIdGenerator())
            .build();
    private final AppConfig appConfig = new AppConfig(methodBuilders, repositoryAdapter, appAdapter);

    private final ContextConfig contextConfig = appConfig.getContextConfig();
    private final ContextPolicyService contextPolicyService = contextConfig.getPolicyService();
    private final ContextFacade contextFacade = contextConfig.getFacade();

    private final IdentityService identityService = appConfig.getIdentityConfig().identityService();

    private final LockoutConfig lockoutConfig = appConfig.getLockoutConfig();
    private final LockoutPolicyService lockoutPolicyService = lockoutConfig.getPolicyService();
    private final LockoutFacade lockoutFacade = lockoutConfig.getFacade();

    @Test
    void shouldThrowExceptionIfNoContextPoliciesConfigured() {
        CreateContextRequest request = FacadeCreateContextRequestMother.build();

        Throwable error = catchThrowable(() -> contextFacade.create(request));

        assertThat(error).isInstanceOf(NoContextPoliciesConfiguredException.class);
    }

    @Test
    void shouldThrowExceptionIfIdentityDoesNotExistWhenCreatingContext() {
        CreateContextRequest request = FacadeCreateContextRequestMother.build();
        givenContextPolicyExistsForChannel(request.getChannelId());

        Throwable error = catchThrowable(() -> contextFacade.create(request));

        assertThat(error).isInstanceOf(IdentityNotFoundException.class);
    }

    @Test
    void shouldThrowExceptionIfLockoutPolicyDoesNotExistingWhenCreatingContext() {
        CreateContextRequest request = FacadeCreateContextRequestMother.build();
        givenContextPolicyExistsForChannel(request.getChannelId());
        givenIdentityExistsForAliases(request.getAliases());

        Throwable error = catchThrowable(() -> contextFacade.create(request));

        assertThat(error).isInstanceOf(NoLockoutPoliciesConfiguredException.class);
    }

    @Test
    void shouldPopulateIdOnContext() {
        CreateContextRequest request = FacadeCreateContextRequestMother.build();
        givenContextPolicyExistsForChannel(request.getChannelId());
        givenIdentityExistsForAliases(request.getAliases());
        givenLockoutPolicyExistsForChannel(request.getChannelId());

        Context context = contextFacade.create(request);

        assertThat(context.getId()).isEqualTo(UUID.fromString("85bbb05a-3cf8-45e5-bae8-430503164c3b"));
    }

    @Test
    void shouldPopulateCreatedTimestampOnContext() {
        CreateContextRequest request = FacadeCreateContextRequestMother.build();
        givenContextPolicyExistsForChannel(request.getChannelId());
        givenIdentityExistsForAliases(request.getAliases());
        givenLockoutPolicyExistsForChannel(request.getChannelId());

        Context context = contextFacade.create(request);

        assertThat(context.getCreated()).isEqualTo(NOW);
    }

    @Test
    void shouldPopulateExpiryTimestampOnContext() {
        CreateContextRequest request = FacadeCreateContextRequestMother.build();
        givenContextPolicyExistsForChannel(request.getChannelId());
        givenIdentityExistsForAliases(request.getAliases());
        givenLockoutPolicyExistsForChannel(request.getChannelId());

        Context context = contextFacade.create(request);

        Duration buffer = Duration.ofMinutes(1);
        Instant expectedExpiry = NOW.plus(context.getDuration()).plus(buffer);
        assertThat(context.getExpiry()).isEqualTo(expectedExpiry);
    }

    @Test
    void shouldPopulateChannelOnContext() {
        CreateContextRequest request = FacadeCreateContextRequestMother.build();
        givenContextPolicyExistsForChannel(request.getChannelId());
        givenIdentityExistsForAliases(request.getAliases());
        givenLockoutPolicyExistsForChannel(request.getChannelId());

        Context context = contextFacade.create(request);

        assertThat(context.getChannel()).isEqualTo(request.getChannel());
    }

    @Test
    void shouldPopulateActivityOnContext() {
        CreateContextRequest request = FacadeCreateContextRequestMother.build();
        givenContextPolicyExistsForChannel(request.getChannelId());
        givenIdentityExistsForAliases(request.getAliases());
        givenLockoutPolicyExistsForChannel(request.getChannelId());

        Context context = contextFacade.create(request);

        assertThat(context.getActivity()).isEqualTo(request.getActivity());
    }

    @Test
    void shouldPopulateIdentityOnContext() {
        CreateContextRequest request = FacadeCreateContextRequestMother.build();
        givenContextPolicyExistsForChannel(request.getChannelId());
        Identity expectedIdentity = givenIdentityExistsForAliases(request.getAliases());
        givenLockoutPolicyExistsForChannel(request.getChannelId());

        Context context = contextFacade.create(request);

        assertThat(context.getIdentity()).usingRecursiveComparison().isEqualTo(expectedIdentity);
    }

    @Test
    void shouldThrowExceptionIfContextNotFound() {
        UUID id = UUID.randomUUID();

        Throwable error = catchThrowable(() -> contextFacade.find(id));

        assertThat(error)
                .isInstanceOf(ContextNotFoundException.class)
                .hasMessage(id.toString());
    }

    @Test
    void shouldReturnContextIfFound() {
        CreateContextRequest request = FacadeCreateContextRequestMother.build();
        givenContextPolicyExistsForChannel(request.getChannelId());
        givenIdentityExistsForAliases(request.getAliases());
        givenLockoutPolicyExistsForChannel(request.getChannelId());
        Context created = contextFacade.create(request);

        Context loaded = contextFacade.find(created.getId());

        assertThat(loaded).isEqualTo(created);
    }

    @Test
    void shouldThrowExceptionIfContextHasExpired() {
        CreateContextRequest request = FacadeCreateContextRequestMother.build();
        givenContextPolicyExistsForChannel(request.getChannelId());
        givenIdentityExistsForAliases(request.getAliases());
        givenLockoutPolicyExistsForChannel(request.getChannelId());
        Context created = contextFacade.create(request);
        UUID id = created.getId();
        clock.plus(created.getDuration().plusMinutes(1).plusMillis(1));

        ContextExpiredException error = catchThrowableOfType(
                () -> contextFacade.find(id),
                ContextExpiredException.class);

        assertThat(error.getId()).isEqualTo(id);
        assertThat(error.getExpiry()).isEqualTo(created.getExpiry());
    }

    @Test
    void shouldPopulateResultOnContext() {
        CreateContextRequest createRequest = FacadeCreateContextRequestMother.build();
        givenContextPolicyExistsForChannel(createRequest.getChannelId());
        givenIdentityExistsForAliases(createRequest.getAliases());
        givenLockoutPolicyExistsForChannel(createRequest.getChannelId());
        Context context = contextFacade.create(createRequest);

        FacadeRecordResultRequest recordRequest = FacadeRecordResultRequestMother.builder()
                .contextId(context.getId())
                .result(ResultMother.withMethodName("fake-method"))
                .build();
        Context updated = contextFacade.record(recordRequest);

        assertThat(updated.isComplete()).isTrue();
    }

    @Test
    void shouldThrowExceptionIfResultMethodIsNotOnContext() {
        CreateContextRequest createRequest = FacadeCreateContextRequestMother.build();
        givenContextPolicyExistsForChannel(createRequest.getChannelId());
        givenIdentityExistsForAliases(createRequest.getAliases());
        givenLockoutPolicyExistsForChannel(createRequest.getChannelId());
        Context context = contextFacade.create(createRequest);

        Result result = ResultMother.withMethodName("another-method");
        FacadeRecordResultRequest recordRequest = FacadeRecordResultRequestMother.builder()
                .contextId(context.getId())
                .result(result)
                .build();
        Throwable error = catchThrowable(() -> contextFacade.record(recordRequest));

        assertThat(error)
                .isInstanceOf(NotNextMethodException.class)
                .hasMessage(result.getMethodName());
    }

    @Test
    void shouldThrowExceptionIfAttemptToPopulateResultOnContextThatIsAlreadyComplete() {
        CreateContextRequest createRequest = FacadeCreateContextRequestMother.build();
        givenContextPolicyExistsForChannel(createRequest.getChannelId());
        givenIdentityExistsForAliases(createRequest.getAliases());
        givenLockoutPolicyExistsForChannel(createRequest.getChannelId());
        Context context = contextFacade.create(createRequest);
        Result result = ResultMother.withMethodName("fake-method");
        FacadeRecordResultRequest recordRequest = FacadeRecordResultRequestMother.builder()
                .contextId(context.getId())
                .result(result)
                .build();
        contextFacade.record(recordRequest);

        Throwable error = catchThrowable(() -> contextFacade.record(recordRequest));

        assertThat(error)
                .isInstanceOf(NotNextMethodException.class)
                .hasMessage(result.getMethodName());
    }

    @Test
    void shouldRecordAttemptsWhenResultRecorded() {
        CreateContextRequest createRequest = FacadeCreateContextRequestMother.build();
        givenContextPolicyExistsForChannel(createRequest.getChannelId());
        givenIdentityExistsForAliases(createRequest.getAliases());
        givenLockoutPolicyExistsForChannel(createRequest.getChannelId());
        Context context = contextFacade.create(createRequest);

        Result result = ResultMother.builder()
                .methodName("fake-method")
                .successful(false)
                .build();
        FacadeRecordResultRequest recordRequest = FacadeRecordResultRequestMother.builder()
                .contextId(context.getId())
                .result(result)
                .build();
        contextFacade.record(recordRequest);
        contextFacade.record(recordRequest);

        ExternalLockoutRequest lockoutRequest = DefaultExternalLockoutRequest.builder()
                .activityName(createRequest.getActivityName())
                .aliases(createRequest.getAliases())
                .channelId(createRequest.getChannelId())
                .build();
        LockoutState state = lockoutFacade.loadState(lockoutRequest);

        assertThat(state.getAttempts()).hasSize(2);
    }

    @Test
    void shouldRecordAttemptsWithCorrectValuesWhenAttemptRecorded() {
        CreateContextRequest createRequest = FacadeCreateContextRequestMother.build();
        givenContextPolicyExistsForChannel(createRequest.getChannelId());
        givenIdentityExistsForAliases(createRequest.getAliases());
        givenLockoutPolicyExistsForChannel(createRequest.getChannelId());
        Context context = contextFacade.create(createRequest);

        Result result = ResultMother.builder()
                .methodName("fake-method")
                .successful(false)
                .build();
        FacadeRecordResultRequest recordRequest = FacadeRecordResultRequestMother.builder()
                .contextId(context.getId())
                .result(result)
                .build();
        contextFacade.record(recordRequest);

        ExternalLockoutRequest lockoutRequest = DefaultExternalLockoutRequest.builder()
                .activityName(createRequest.getActivityName())
                .aliases(createRequest.getAliases())
                .channelId(createRequest.getChannelId())
                .build();
        LockoutState state = lockoutFacade.loadState(lockoutRequest);

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
        givenContextPolicyExistsForChannel(createRequest.getChannelId());
        Identity identity = givenIdentityExistsForAliases(createRequest.getAliases());
        givenLockoutPolicyExistsForChannel(createRequest.getChannelId());
        Context context = contextFacade.create(createRequest);

        Result result = ResultMother.builder()
                .methodName("fake-method")
                .successful(false)
                .build();
        FacadeRecordResultRequest recordRequest = FacadeRecordResultRequestMother.builder()
                .contextId(context.getId())
                .result(result)
                .build();
        contextFacade.record(recordRequest);
        contextFacade.record(recordRequest);
        contextFacade.record(recordRequest);

        Throwable error = catchThrowable(() -> contextFacade.record(recordRequest));

        assertThat(error)
                .isInstanceOf(LockedOutException.class)
                .hasMessage(identity.getIdvId().format());
    }

    @Test
    void shouldNotRecordAttemptIfPolicyIsRecordOnMethodCompleteAndMethodIsNotComplete() {
        CreateContextRequest createRequest = FacadeCreateContextRequestMother.build();
        givenContextPolicyExistsForChannel(createRequest.getChannelId());
        givenIdentityExistsForAliases(createRequest.getAliases());
        LockoutPolicy lockoutPolicy = LockoutPolicyMother.builder()
                .key(ChannelPolicyKeyMother.withChannelId(createRequest.getChannelId()))
                .recordAttemptPolicy(new RecordAttemptWhenMethodCompletePolicy())
                .build();
        givenLockoutPolicyExists(lockoutPolicy);
        Context context = contextFacade.create(createRequest);

        Result result = ResultMother.builder()
                .methodName("fake-method")
                .successful(false)
                .build();
        FacadeRecordResultRequest recordRequest = FacadeRecordResultRequestMother.builder()
                .contextId(context.getId())
                .result(result)
                .build();
        contextFacade.record(recordRequest);

        ExternalLockoutRequest lockoutRequest = DefaultExternalLockoutRequest.builder()
                .activityName(createRequest.getActivityName())
                .aliases(createRequest.getAliases())
                .channelId(createRequest.getChannelId())
                .build();
        LockoutState state = lockoutFacade.loadState(lockoutRequest);

        assertThat(state.getAttempts()).isEmpty();
    }

    @Test
    void shouldNotRecordAttemptIfPolicyIsNeverRecord() {
        CreateContextRequest createRequest = FacadeCreateContextRequestMother.build();
        givenContextPolicyExistsForChannel(createRequest.getChannelId());
        givenIdentityExistsForAliases(createRequest.getAliases());
        LockoutPolicy lockoutPolicy = LockoutPolicyMother.builder()
                .key(ChannelPolicyKeyMother.withChannelId(createRequest.getChannelId()))
                .recordAttemptPolicy(new NeverRecordAttemptPolicy())
                .build();
        givenLockoutPolicyExists(lockoutPolicy);
        Context context = contextFacade.create(createRequest);

        Result result = ResultMother.builder()
                .methodName("fake-method")
                .successful(false)
                .build();
        FacadeRecordResultRequest recordRequest = FacadeRecordResultRequestMother.builder()
                .contextId(context.getId())
                .result(result)
                .build();
        contextFacade.record(recordRequest);

        ExternalLockoutRequest lockoutRequest = DefaultExternalLockoutRequest.builder()
                .activityName(createRequest.getActivityName())
                .aliases(createRequest.getAliases())
                .channelId(createRequest.getChannelId())
                .build();
        LockoutState state = lockoutFacade.loadState(lockoutRequest);

        assertThat(state.getAttempts()).isEmpty();
    }

    @Test
    void shouldBeLockedIfHardLockoutPolicyConfiguredAndMaxAttemptsReached() {
        CreateContextRequest createRequest = FacadeCreateContextRequestMother.build();
        givenContextPolicyExistsForChannel(createRequest.getChannelId());
        givenIdentityExistsForAliases(createRequest.getAliases());
        LockoutPolicy lockoutPolicy = LockoutPolicyMother.builder()
                .key(ChannelPolicyKeyMother.withChannelId(createRequest.getChannelId()))
                .stateCalculator(HardLockoutStateCalculatorMother.withMaxNumberOfAttempts(2))
                .build();
        givenLockoutPolicyExists(lockoutPolicy);
        Context context = contextFacade.create(createRequest);

        Result result = ResultMother.builder()
                .methodName("fake-method")
                .successful(false)
                .build();
        FacadeRecordResultRequest recordRequest = FacadeRecordResultRequestMother.builder()
                .contextId(context.getId())
                .result(result)
                .build();
        contextFacade.record(recordRequest);
        contextFacade.record(recordRequest);
        LockedOutException error = catchThrowableOfType(
                () -> contextFacade.record(recordRequest),
                LockedOutException.class
        );

        LockoutState state = error.getState();
        assertThat(state.isLocked()).isTrue();
        assertThat(state.getMessage()).isEqualTo("maximum number of attempts [2] reached");
    }

    @Test
    void shouldBeLockedIfSoftLockoutPolicyConfiguredAndBoundaryAttemptsReached() {
        CreateContextRequest createRequest = FacadeCreateContextRequestMother.build();
        givenContextPolicyExistsForChannel(createRequest.getChannelId());
        givenIdentityExistsForAliases(createRequest.getAliases());
        LockoutPolicy lockoutPolicy = LockoutPolicyMother.builder()
                .key(ChannelPolicyKeyMother.withChannelId(createRequest.getChannelId()))
                .stateCalculator(SoftLockoutStateCalculatorMother.build())
                .build();
        givenLockoutPolicyExists(lockoutPolicy);
        Context context = contextFacade.create(createRequest);

        Result result = ResultMother.builder()
                .methodName("fake-method")
                .successful(false)
                .build();
        FacadeRecordResultRequest recordRequest = FacadeRecordResultRequestMother.builder()
                .contextId(context.getId())
                .result(result)
                .build();
        contextFacade.record(recordRequest);
        LockedOutException error = catchThrowableOfType(
                () -> contextFacade.record(recordRequest),
                LockedOutException.class
        );

        LockoutState state = error.getState();
        assertThat(state.isLocked()).isTrue();
        assertThat(state.getMessage()).isEqualTo("soft lock began at 2020-10-01T19:10:22Z and expiring at 2020-10-01T19:11:22Z");
    }

    @Test
    void shouldResetLockoutStateOnSuccessfulAttempt() {
        CreateContextRequest createRequest = FacadeCreateContextRequestMother.build();
        givenContextPolicyExistsForChannel(createRequest.getChannelId());
        givenIdentityExistsForAliases(createRequest.getAliases());
        givenLockoutPolicyExistsForChannel(createRequest.getChannelId());
        Context context = contextFacade.create(createRequest);

        Result result = ResultMother.builder()
                .methodName("fake-method")
                .successful(false)
                .build();
        FacadeRecordResultRequest recordUnsuccessfulRequest = FacadeRecordResultRequestMother.builder()
                .contextId(context.getId())
                .result(result)
                .build();
        contextFacade.record(recordUnsuccessfulRequest);
        FacadeRecordResultRequest recordSuccessfulRequest = FacadeRecordResultRequestMother.builder()
                .contextId(context.getId())
                .result(ResultMother.withMethodName("fake-method"))
                .build();
        contextFacade.record(recordSuccessfulRequest);

        ExternalLockoutRequest lockoutRequest = DefaultExternalLockoutRequest.builder()
                .activityName(createRequest.getActivityName())
                .aliases(createRequest.getAliases())
                .channelId(createRequest.getChannelId())
                .build();
        LockoutState state = lockoutFacade.loadState(lockoutRequest);

        assertThat(state.isLocked()).isFalse();
        assertThat(state.getAttempts()).isEmpty();
        assertThat(state.getMessage()).contains("");
    }

    private void givenContextPolicyExistsForChannel(String channelId) {
        ContextPolicy policy = ContextPolicyMother.builder()
                .key(ChannelPolicyKeyMother.withChannelId(channelId))
                .sequencePolicies(SequencePoliciesMother.withMethodPolicy(FakeMethodPolicyMother.build()))
                .build();
        contextPolicyService.create(policy);
    }

    private Identity givenIdentityExistsForAliases(Aliases aliases) {
        return givenIdentityExists(IdentityMother.withAliases(aliases));
    }

    private Identity givenIdentityExists(Identity identity) {
        return identityService.update(identity);
    }

    private void givenLockoutPolicyExistsForChannel(String channelId) {
        PolicyKey key = ChannelPolicyKeyMother.withChannelId(channelId);
        LockoutPolicy policy = LockoutPolicyMother.builder()
                .key(key)
                .attemptsFilter(new AttemptsFilter(key))
                .build();
        givenLockoutPolicyExists(policy);
    }

    private void givenLockoutPolicyExists(LockoutPolicy policy) {
        lockoutPolicyService.create(policy);
    }

}
