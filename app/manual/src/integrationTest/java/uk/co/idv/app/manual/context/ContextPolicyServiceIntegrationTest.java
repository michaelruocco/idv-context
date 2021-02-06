package uk.co.idv.app.manual.context;

import org.junit.jupiter.api.Test;
import uk.co.idv.app.manual.Application;
import uk.co.idv.app.manual.TestHarness;
import uk.co.idv.app.manual.otp.AbcPolicyMother;
import uk.co.idv.app.manual.otp.GbRsaPolicyMother;
import uk.co.idv.context.entities.policy.ContextPolicy;
import uk.co.idv.context.entities.policy.ContextPolicyMother;
import uk.co.idv.context.entities.policy.sequence.SequencePoliciesMother;
import uk.co.idv.context.entities.policy.sequence.SequencePolicyMother;
import uk.co.idv.policy.entities.policy.Policies;
import uk.co.idv.policy.entities.policy.PolicyNotFoundException;
import uk.co.idv.policy.entities.policy.PolicyRequest;
import uk.co.idv.policy.entities.policy.PolicyRequestMother;
import uk.co.idv.policy.entities.policy.key.ChannelPolicyKeyMother;
import uk.co.idv.policy.entities.policy.key.PolicyKey;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class ContextPolicyServiceIntegrationTest {

    private final TestHarness harness = new TestHarness();
    private final Application application = harness.getApplication();

    @Test
    void shouldThrowExceptionIfPolicyNotFoundById() {
        UUID id = UUID.randomUUID();

        Throwable error = catchThrowable(() -> application.loadContextPolicy(id));

        assertThat(error)
                .isInstanceOf(PolicyNotFoundException.class)
                .hasMessage(id.toString());
    }

    @Test
    void shouldReturnEmptyPoliciesByKeyIfNoPoliciesFound() {
        PolicyKey key = ChannelPolicyKeyMother.build();

        Policies<ContextPolicy> policies = application.loadContextPolicies(key);

        assertThat(policies).isEmpty();
    }

    @Test
    void shouldReturnEmptyPoliciesIfLoadAllWhenNoPoliciesSaved() {
        Policies<ContextPolicy> policies = application.loadAllContextPolicies();

        assertThat(policies).isEmpty();
    }

    @Test
    void shouldLoadCreatedContextPolicyById() {
        ContextPolicy expected = ContextPolicyMother.build();
        application.create(expected);

        ContextPolicy loaded = application.loadContextPolicy(expected.getId());

        assertThat(loaded).isEqualTo(expected);
    }

    @Test
    void shouldLoadCreatedContextPolicyByPolicyRequest() {
        ContextPolicy expected = ContextPolicyMother.build();
        application.create(expected);
        PolicyRequest request = PolicyRequestMother.build();

        Policies<ContextPolicy> loaded = application.loadContextPolicies(request);

        assertThat(loaded).containsExactly(expected);
    }

    @Test
    void shouldLoadCreatedContextPolicyByKey() {
        ContextPolicy expected = ContextPolicyMother.build();
        application.create(expected);

        Policies<ContextPolicy> loaded = application.loadContextPolicies(expected.getKey());

        assertThat(loaded).containsExactly(expected);
    }

    @Test
    void shouldLoadCreatedContextPolicyWhenAllPoliciesLoaded() {
        ContextPolicy expected = ContextPolicyMother.build();
        application.create(expected);

        Policies<ContextPolicy> loaded = application.loadAllContextPolicies();

        assertThat(loaded).containsExactly(expected);
    }

    @Test
    void shouldThrowExceptionIfAttemptToUpdatePolicyThatDoesNotExist() {
        ContextPolicy policy = ContextPolicyMother.build();

        Throwable error = catchThrowable(() -> application.update(policy));

        assertThat(error)
                .isInstanceOf(PolicyNotFoundException.class)
                .hasMessage(policy.getId().toString());
    }

    @Test
    void shouldUpdatePolicy() {
        ContextPolicy initial = ContextPolicyMother.withPolicy(SequencePolicyMother.withName("sequence"));
        application.create(initial);
        ContextPolicy update = ContextPolicyMother.withPolicy(SequencePolicyMother.withName("updated-sequence"));

        application.update(update);

        ContextPolicy loaded = application.loadContextPolicy(update.getId());
        assertThat(loaded).isEqualTo(update);
    }

    @Test
    void shouldDeletePolicy() {
        ContextPolicy policy1 = ContextPolicyMother.withKey(ChannelPolicyKeyMother.withId(UUID.randomUUID()));
        ContextPolicy policy2 = ContextPolicyMother.withKey(ChannelPolicyKeyMother.withId(UUID.randomUUID()));
        application.create(policy1);
        application.create(policy2);

        application.deleteContextPolicy(policy1.getId());

        Policies<ContextPolicy> policies = application.loadAllContextPolicies();
        assertThat(policies).containsExactly(policy2);
    }

    @Test
    void shouldPopulateConfiguredPolicies() {
        application.populatePolicies(harness.getChannelAdapter());

        Policies<ContextPolicy> policies = application.loadAllContextPolicies();

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
        application.create(existingPolicy);

        application.populatePolicies(harness.getChannelAdapter());

        Policies<ContextPolicy> policies = application.loadAllContextPolicies();
        assertThat(policies).containsExactlyInAnyOrder(
                existingPolicy,
                GbRsaPolicyMother.gbRsaContextPolicy()
        );
    }

}
