package uk.co.idv.app.manual.context;

import org.junit.jupiter.api.Test;
import uk.co.idv.app.manual.AppConfig;
import uk.co.idv.app.manual.JsonConfig;
import uk.co.idv.app.manual.adapter.app.AppAdapter;
import uk.co.idv.app.manual.adapter.app.DefaultAppAdapter;
import uk.co.idv.app.manual.adapter.channel.ChannelAdapter;
import uk.co.idv.app.manual.adapter.channel.DefaultChannelAdapter;
import uk.co.idv.app.manual.adapter.repository.InMemoryRepositoryAdapter;
import uk.co.idv.app.manual.adapter.repository.RepositoryAdapter;
import uk.co.idv.app.manual.otp.AbcPolicyMother;
import uk.co.idv.app.manual.otp.GbRsaPolicyMother;
import uk.co.idv.context.adapter.method.otp.delivery.phone.simswap.StubSimSwapExecutorConfig;
import uk.co.idv.context.entities.policy.ContextPolicy;
import uk.co.idv.context.entities.policy.ContextPolicyMother;
import uk.co.idv.context.entities.policy.sequence.SequencePoliciesMother;
import uk.co.idv.context.entities.policy.sequence.SequencePolicyMother;
import uk.co.idv.context.usecases.policy.ContextPoliciesPopulator;
import uk.co.idv.context.usecases.policy.ContextPolicyService;
import uk.co.idv.method.adapter.json.method.MethodMappings;
import uk.co.idv.method.adapter.json.fake.FakeMethodMapping;
import uk.co.idv.method.adapter.json.otp.OtpMapping;
import uk.co.idv.method.config.AppMethodConfig;
import uk.co.idv.method.config.otp.AppOtpConfig;
import uk.co.idv.method.usecases.MethodBuilders;
import uk.co.idv.policy.entities.policy.Policies;
import uk.co.idv.policy.entities.policy.PolicyRequest;
import uk.co.idv.policy.entities.policy.PolicyRequestMother;
import uk.co.idv.policy.entities.policy.key.ChannelPolicyKeyMother;
import uk.co.idv.policy.entities.policy.key.PolicyKey;
import uk.co.idv.policy.usecases.policy.load.PolicyNotFoundException;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class ContextPolicyServiceIntegrationTest {

    private final RepositoryAdapter repositoryAdapter = new InMemoryRepositoryAdapter();
    private final AppAdapter appAdapter = DefaultAppAdapter.builder().build();
    private final AppMethodConfig otpConfig = AppOtpConfig.builder()
            .simSwapExecutorConfig(StubSimSwapExecutorConfig.buildDefault())
            .clock(appAdapter.getClock())
            .idGenerator(appAdapter.getIdGenerator())
            .contextRepository(repositoryAdapter.getContextRepository())
            .build();
    private final MethodBuilders methodBuilders = new MethodBuilders(otpConfig.methodBuilder());
    private final AppConfig appConfig = new AppConfig(methodBuilders, repositoryAdapter, appAdapter);
    private final JsonConfig jsonConfig = new JsonConfig(new MethodMappings(new FakeMethodMapping(), new OtpMapping()));
    private final ChannelAdapter channelAdapter = new DefaultChannelAdapter(jsonConfig.getJsonConverter());

    private final ContextPolicyService policyService = appConfig.getContextConfig().getPolicyService();

    @Test
    void shouldThrowExceptionIfPolicyNotFoundById() {
        UUID id = UUID.randomUUID();

        Throwable error = catchThrowable(() -> policyService.load(id));

        assertThat(error)
                .isInstanceOf(PolicyNotFoundException.class)
                .hasMessage(id.toString());
    }

    @Test
    void shouldReturnEmptyPoliciesByKeyIfNoPoliciesFound() {
        PolicyKey key = ChannelPolicyKeyMother.build();

        Policies<ContextPolicy> policies = policyService.load(key);

        assertThat(policies).isEmpty();
    }

    @Test
    void shouldReturnEmptyPoliciesIfLoadAllWhenNoPoliciesSaved() {
        Policies<ContextPolicy> policies = policyService.loadAll();

        assertThat(policies).isEmpty();
    }

    @Test
    void shouldLoadCreatedContextPolicyById() {
        ContextPolicy expected = ContextPolicyMother.build();
        policyService.create(expected);

        ContextPolicy loaded = policyService.load(expected.getId());

        assertThat(loaded).isEqualTo(expected);
    }

    @Test
    void shouldLoadCreatedContextPolicyByPolicyRequest() {
        ContextPolicy expected = ContextPolicyMother.build();
        policyService.create(expected);
        PolicyRequest request = PolicyRequestMother.build();

        Policies<ContextPolicy> loaded = policyService.load(request);

        assertThat(loaded).containsExactly(expected);
    }

    @Test
    void shouldLoadCreatedContextPolicyByKey() {
        ContextPolicy expected = ContextPolicyMother.build();
        policyService.create(expected);

        Policies<ContextPolicy> loaded = policyService.load(expected.getKey());

        assertThat(loaded).containsExactly(expected);
    }

    @Test
    void shouldLoadCreatedContextPolicyWhenAllPoliciesLoaded() {
        ContextPolicy expected = ContextPolicyMother.build();
        policyService.create(expected);

        Policies<ContextPolicy> loaded = policyService.loadAll();

        assertThat(loaded).containsExactly(expected);
    }

    @Test
    void shouldThrowExceptionIfAttemptToUpdatePolicyThatDoesNotExist() {
        ContextPolicy policy = ContextPolicyMother.build();

        Throwable error = catchThrowable(() -> policyService.update(policy));

        assertThat(error)
                .isInstanceOf(PolicyNotFoundException.class)
                .hasMessage(policy.getId().toString());
    }

    @Test
    void shouldUpdatePolicy() {
        ContextPolicy initial = ContextPolicyMother.withPolicy(SequencePolicyMother.withName("sequence"));
        policyService.create(initial);
        ContextPolicy update = ContextPolicyMother.withPolicy(SequencePolicyMother.withName("updated-sequence"));

        policyService.update(update);

        ContextPolicy loaded = policyService.load(update.getId());
        assertThat(loaded).isEqualTo(update);
    }

    @Test
    void shouldDeletePolicy() {
        ContextPolicy policy1 = ContextPolicyMother.withKey(ChannelPolicyKeyMother.withId(UUID.randomUUID()));
        ContextPolicy policy2 = ContextPolicyMother.withKey(ChannelPolicyKeyMother.withId(UUID.randomUUID()));
        policyService.create(policy1);
        policyService.create(policy2);

        policyService.delete(policy1.getId());

        Policies<ContextPolicy> policies = policyService.loadAll();
        assertThat(policies).containsExactly(policy2);
    }

    @Test
    void shouldPopulateConfiguredPolicies() {
        ContextPoliciesPopulator populator = appConfig.getContextConfig().getPoliciesPopulator();
        populator.populate(channelAdapter.getContextPolicies());

        Policies<ContextPolicy> policies = policyService.loadAll();

        assertThat(policies).containsExactlyInAnyOrder(
                AbcPolicyMother.abcContextPolicy(),
                GbRsaPolicyMother.gbRsaContextPolicy()
        );
    }

    @Test
    void shouldNotOverwriteExistingPoliciesWhenPopulatingConfiguredPolicies() {
        ContextPolicy abcPolicy = AbcPolicyMother.abcContextPolicy();
        ContextPolicy existingPolicy = ContextPolicyMother.builder()
                .key(abcPolicy.getKey())
                .sequencePolicies(SequencePoliciesMother.empty())
                .build();
        policyService.create(existingPolicy);

        ContextPoliciesPopulator populator = appConfig.getContextConfig().getPoliciesPopulator();
        populator.populate(channelAdapter.getContextPolicies());

        Policies<ContextPolicy> policies = policyService.loadAll();
        assertThat(policies).containsExactlyInAnyOrder(
                existingPolicy,
                GbRsaPolicyMother.gbRsaContextPolicy()
        );
    }

}
