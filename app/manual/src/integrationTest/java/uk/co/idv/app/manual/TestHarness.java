package uk.co.idv.app.manual;

import uk.co.idv.app.manual.adapter.channel.ChannelAdapter;
import uk.co.idv.app.manual.adapter.channel.DefaultChannelAdapter;
import uk.co.idv.app.manual.config.AppConfig;
import uk.co.idv.app.manual.adapter.app.AppAdapter;
import uk.co.idv.app.manual.adapter.app.DefaultAppAdapter;
import uk.co.idv.app.manual.adapter.repository.InMemoryRepositoryAdapter;
import uk.co.idv.app.manual.adapter.repository.RepositoryAdapter;
import uk.co.idv.app.manual.clock.UpdatableClock;
import uk.co.idv.app.manual.config.JsonConfig;
import uk.co.idv.common.usecases.id.IdGenerator;
import uk.co.idv.common.usecases.id.NonRandomIdGenerator;
import uk.co.idv.context.adapter.method.otp.delivery.phone.simswap.StubSimSwapExecutorConfig;
import uk.co.idv.context.entities.policy.ContextPolicy;
import uk.co.idv.context.entities.policy.ContextPolicyMother;
import uk.co.idv.context.entities.policy.sequence.SequencePoliciesMother;
import uk.co.idv.context.entities.result.FacadeRecordResultRequest;
import uk.co.idv.context.entities.result.FacadeRecordResultRequestMother;
import uk.co.idv.identity.entities.alias.Aliases;
import uk.co.idv.identity.entities.identity.Identity;
import uk.co.idv.identity.entities.identity.IdentityMother;
import uk.co.idv.lockout.entities.policy.AttemptsFilter;
import uk.co.idv.lockout.entities.policy.LockoutPolicy;
import uk.co.idv.lockout.entities.policy.LockoutPolicyMother;
import uk.co.idv.method.adapter.json.fake.FakeMethodMapping;
import uk.co.idv.method.adapter.json.method.MethodMappings;
import uk.co.idv.method.adapter.json.otp.OtpMapping;
import uk.co.idv.method.config.AppFakeMethodConfig;
import uk.co.idv.method.config.AppMethodConfig;
import uk.co.idv.method.config.otp.AppOtpConfig;
import uk.co.idv.method.entities.method.fake.policy.FakeMethodPolicyMother;
import uk.co.idv.method.entities.policy.MethodPolicy;
import uk.co.idv.method.entities.result.Result;
import uk.co.idv.method.entities.result.ResultMother;
import uk.co.idv.method.usecases.MethodBuilders;
import uk.co.idv.policy.entities.policy.key.ChannelPolicyKeyMother;
import uk.co.idv.policy.entities.policy.key.PolicyKey;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

public class TestHarness {

    private static final Instant NOW = Instant.parse("2020-10-06T21:00:00.000Z");

    private final UpdatableClock clock = new UpdatableClock(NOW);
    private final IdGenerator idGenerator = new NonRandomIdGenerator();
    private final RepositoryAdapter repositoryAdapter = new InMemoryRepositoryAdapter();

    private final AppMethodConfig otpConfig = AppOtpConfig.builder()
            .simSwapExecutorConfig(StubSimSwapExecutorConfig.buildDefault())
            .clock(clock)
            .idGenerator(idGenerator)
            .contextRepository(repositoryAdapter.getContextRepository())
            .build();

    private final AppMethodConfig fakeMethodConfig = new AppFakeMethodConfig();

    private final MethodBuilders methodBuilders = new MethodBuilders(
            fakeMethodConfig.methodBuilder(),
            otpConfig.methodBuilder()
    );

    private final AppAdapter appAdapter = DefaultAppAdapter.builder()
            .repositoryAdapter(repositoryAdapter)
            .clock(clock)
            .idGenerator(idGenerator)
            .build();

    private final AppConfig appConfig = new AppConfig(methodBuilders, repositoryAdapter, appAdapter);

    private final JsonConfig jsonConfig = new JsonConfig(new MethodMappings(new FakeMethodMapping(), new OtpMapping()));
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

    public void givenLockoutPolicyExistsForChannel(String channelId) {
        PolicyKey key = ChannelPolicyKeyMother.withChannelId(channelId);
        LockoutPolicy policy = LockoutPolicyMother.builder()
                .key(key)
                .attemptsFilter(new AttemptsFilter(key))
                .build();
        givenLockoutPolicyExists(policy);
    }

    public void givenLockoutPolicyExists(LockoutPolicy policy) {
        application.create(policy);
    }

    public void givenUnsuccessfulResultRecorded(UUID contextId) {
        Result result = ResultMother.builder()
                .methodName("fake-method")
                .successful(false)
                .build();
        FacadeRecordResultRequest recordRequest = FacadeRecordResultRequestMother.builder()
                .contextId(contextId)
                .result(result)
                .build();
        application.record(recordRequest);
    }

}
