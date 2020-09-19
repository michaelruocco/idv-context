package uk.co.idv.app.manual.context;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.config.ContextFacadeConfig;
import uk.co.idv.context.config.repository.inmemory.InMemoryContextRepositoryConfig;
import uk.co.idv.context.entities.policy.ContextPolicy;
import uk.co.idv.context.entities.policy.ContextPolicyMother;
import uk.co.idv.context.entities.policy.sequence.SequencePolicyMother;
import uk.co.idv.context.usecases.policy.ContextPolicyService;
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

    private final ContextFacadeConfig contextConfig = ContextFacadeConfig.builder()
            .repositoryConfig(new InMemoryContextRepositoryConfig())
            .build();

    private final ContextPolicyService policyService = contextConfig.policyService();

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

}
