package uk.co.idv.app.plain;

import uk.co.idv.app.plain.adapter.channel.ChannelAdapter;
import uk.co.idv.app.plain.adapter.channel.DefaultChannelAdapter;
import uk.co.idv.app.plain.config.AppConfig;
import uk.co.idv.app.plain.adapter.app.AppAdapter;
import uk.co.idv.app.plain.adapter.app.DefaultAppAdapter;
import uk.co.idv.app.plain.adapter.repository.InMemoryRepositoryAdapter;
import uk.co.idv.app.plain.adapter.repository.RepositoryAdapter;
import uk.co.idv.app.plain.config.JsonConfig;
import uk.co.idv.context.adapter.method.otp.delivery.phone.simswap.StubSimSwapExecutorConfig;
import uk.co.idv.context.entities.policy.ContextPolicy;
import uk.co.idv.context.entities.policy.ContextPolicyMother;
import uk.co.idv.context.entities.policy.sequence.SequencePoliciesMother;
import uk.co.idv.method.adapter.json.push.PushNotificationMapping;
import uk.co.idv.method.entities.verification.CompleteVerificationRequest;
import uk.co.idv.method.entities.verification.CompleteVerificationRequestMother;
import uk.co.idv.method.entities.verification.CreateVerificationRequest;
import uk.co.idv.method.entities.verification.Verification;
import uk.co.idv.identity.adapter.eligibility.external.StubExternalFindIdentityConfig;
import uk.co.idv.identity.config.ExternalFindIdentityConfig;
import uk.co.idv.identity.entities.alias.Aliases;
import uk.co.idv.identity.entities.identity.Identity;
import uk.co.idv.identity.entities.identity.IdentityMother;
import uk.co.idv.lockout.entities.policy.AttemptsFilter;
import uk.co.idv.lockout.entities.policy.LockoutPolicy;
import uk.co.idv.lockout.entities.policy.hard.HardLockoutPolicyMother;
import uk.co.idv.lockout.entities.policy.hard.HardLockoutStateCalculatorMother;
import uk.co.idv.lockout.entities.policy.includeattempt.IncludeAttemptsPolicy;
import uk.co.idv.lockout.entities.policy.includeattempt.IncludeAttemptsWithinDurationPolicyMother;
import uk.co.idv.method.adapter.json.fake.FakeMethodMapping;
import uk.co.idv.method.adapter.json.method.MethodMappings;
import uk.co.idv.method.adapter.json.otp.OtpMapping;
import uk.co.idv.method.config.AppFakeMethodConfig;
import uk.co.idv.method.config.AppMethodConfig;
import uk.co.idv.method.config.AppMethodConfigs;
import uk.co.idv.method.config.otp.AppOtpConfig;
import uk.co.idv.method.entities.method.fake.policy.FakeMethodPolicyMother;
import uk.co.idv.method.entities.policy.MethodPolicy;
import uk.co.idv.policy.entities.policy.key.channel.ChannelPolicyKeyMother;
import uk.co.idv.policy.entities.policy.key.PolicyKey;
import uk.co.mruoc.randomvalue.uuid.NonRandomUuidGenerator;
import uk.co.mruoc.randomvalue.uuid.UuidGenerator;
import uk.co.mruoc.test.clock.OverridableClock;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.Executors;

public class TestHarness {

    private static final Instant NOW = Instant.parse("2020-10-06T21:00:00.000Z");

    private final OverridableClock clock = new OverridableClock(NOW);
    private final UuidGenerator uuidGenerator = new NonRandomUuidGenerator();
    private final RepositoryAdapter repositoryAdapter = new InMemoryRepositoryAdapter();

    private final AppMethodConfig otpConfig = AppOtpConfig.builder()
            .simSwapExecutorConfig(StubSimSwapExecutorConfig.withFixedDelay())
            .clock(clock)
            .uuidGenerator(uuidGenerator)
            .contextRepository(repositoryAdapter.getContextRepository())
            .build();

    private final AppMethodConfigs appMethodConfigs = new AppMethodConfigs(
            new AppFakeMethodConfig(),
            otpConfig
    );

    private final AppAdapter appAdapter = DefaultAppAdapter.builder()
            .clock(clock)
            .uuidGenerator(uuidGenerator)
            .build();

    private final ExternalFindIdentityConfig externalFindIdentityConfig = StubExternalFindIdentityConfig.builder()
            .executor(Executors.newCachedThreadPool())
            .timeout(Duration.ofMillis(250))
            .phoneNumberDelay(Duration.ofMillis(400))
            .emailAddressDelay(Duration.ofMillis(100))
            .build();

    private final AppConfig appConfig = new AppConfig(
            appMethodConfigs,
            repositoryAdapter,
            appAdapter,
            externalFindIdentityConfig
    );

    private final MethodMappings methodMappings = new MethodMappings(new FakeMethodMapping(), new OtpMapping(), new PushNotificationMapping());
    private final JsonConfig jsonConfig = new JsonConfig(clock, methodMappings);
    private final ChannelAdapter channelAdapter = new DefaultChannelAdapter(jsonConfig.getJsonConverter());

    private final Application application = new Application(appConfig);

    public Instant now() {
        return clock.instant();
    }

    public void fastForwardTimeBy(Duration duration) {
        clock.plus(duration);
    }

    public Application getApplication() {
        return application;
    }

    public ChannelAdapter getChannelAdapter() {
        return channelAdapter;
    }

    public void givenContextPolicyExistsForChannel(String channelId) {
        givenContextPolicyExistsForChannel(channelId, FakeMethodPolicyMother.build());
    }

    public void givenContextPolicyExistsForChannel(String channelId, MethodPolicy methodPolicy) {
        ContextPolicy policy = ContextPolicyMother.builder()
                .key(ChannelPolicyKeyMother.withChannelId(channelId))
                .sequencePolicies(SequencePoliciesMother.withMethodPolicy(methodPolicy))
                .build();
        application.create(policy);
    }

    public Identity givenIdentityExistsForAliases(Aliases aliases) {
        return givenIdentityExists(IdentityMother.withAliases(aliases));
    }

    public Identity givenIdentityExists(Identity identity) {
        return application.update(identity);
    }

    public void givenHardLockoutPolicyWithIncludeAttemptsWithin24HoursExistsForChannel(String channelId) {
        IncludeAttemptsPolicy includeAttemptsPolicy = IncludeAttemptsWithinDurationPolicyMother.withClock(clock);
        PolicyKey key = ChannelPolicyKeyMother.withChannelId(channelId);
        LockoutPolicy policy = HardLockoutPolicyMother.builder()
                .key(key)
                .attemptsFilter(new AttemptsFilter(key))
                .stateCalculator(HardLockoutStateCalculatorMother.withIncludeAttemptsPolicy(includeAttemptsPolicy))
                .build();
        givenLockoutPolicyExists(policy);
    }

    public void givenHardLockoutPolicyExistsForChannel(String channelId) {
        PolicyKey key = ChannelPolicyKeyMother.withChannelId(channelId);
        LockoutPolicy policy = HardLockoutPolicyMother.withKey(key);
        givenLockoutPolicyExists(policy);
    }

    public void givenLockoutPolicyExists(LockoutPolicy policy) {
        application.create(policy);
    }

    public Verification givenVerificationCompletedUnsuccessfully(CreateVerificationRequest request) {
        Verification verification = givenVerificationCreated(request);
        return givenVerificationCompleted(verification, false);
    }

    public Verification givenVerificationCompletedSuccessfully(CreateVerificationRequest request) {
        Verification verification = givenVerificationCreated(request);
        return givenVerificationCompleted(verification, true);
    }

    private Verification givenVerificationCreated(CreateVerificationRequest request) {
        return application.create(request);
    }

    private Verification givenVerificationCompleted(Verification verification, boolean successful) {
        CompleteVerificationRequest request = CompleteVerificationRequestMother.builder()
                .id(verification.getId())
                .contextId(verification.getContextId())
                .successful(successful)
                .timestamp(clock.instant())
                .build();
        return application.complete(request).getVerification();
    }

}
