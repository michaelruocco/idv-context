package uk.co.idv.policy.usecases.policy;

import org.junit.jupiter.api.Test;
import uk.co.idv.policy.entities.policy.DefaultPolicyRequest;
import uk.co.idv.policy.entities.policy.MockPolicyMother;
import uk.co.idv.policy.entities.policy.Policies;
import uk.co.idv.policy.entities.policy.Policy;
import uk.co.idv.policy.entities.policy.key.PolicyKey;
import uk.co.idv.policy.entities.policy.PolicyRequest;
import uk.co.idv.policy.usecases.policy.create.CreatePolicy;
import uk.co.idv.policy.usecases.policy.delete.DeletePolicy;
import uk.co.idv.policy.usecases.policy.load.LoadPolicy;
import uk.co.idv.policy.usecases.policy.update.UpdatePolicy;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class PolicyServiceTest {

    private final CreatePolicy<Policy> create = mock(CreatePolicy.class);
    private final UpdatePolicy<Policy> update = mock(UpdatePolicy.class);
    private final LoadPolicy<Policy> load = mock(LoadPolicy.class);
    private final DeletePolicy<Policy> delete = mock(DeletePolicy.class);
    private final NoPoliciesConfiguredHandler<NoPoliciesConfiguredException> handler = mock(NoPoliciesConfiguredHandler.class);

    private final PolicyService<Policy, NoPoliciesConfiguredException> service = new PolicyService<>(
            create,
            update,
            load,
            delete,
            handler
    );

    @Test
    void shouldCreatePolicy() {
        Policy policy = MockPolicyMother.policy();

        service.create(policy);

        verify(create).create(policy);
    }

    @Test
    void shouldUpdatePolicy() {
        Policy policy = MockPolicyMother.policy();

        service.update(policy);

        verify(update).update(policy);
    }

    @Test
    void shouldDeletePolicy() {
        UUID id = UUID.randomUUID();

        service.delete(id);

        verify(delete).delete(id);
    }

    @Test
    void shouldLoadPolicyById() {
        UUID id = UUID.randomUUID();
        Policy expectedPolicy = MockPolicyMother.withId(id);
        given(load.load(id)).willReturn(expectedPolicy);

        Policy policy = service.load(id);

        assertThat(policy).isEqualTo(expectedPolicy);
    }

    @Test
    void shouldLoadPoliciesByRequest() {
        PolicyRequest request = mock(DefaultPolicyRequest.class);
        Policies<Policy> expectedPolicies = mock(Policies.class);
        given(load.load(request)).willReturn(expectedPolicies);

        Policies<Policy> policies = service.load(request);

        assertThat(policies).isEqualTo(expectedPolicies);
    }

    @Test
    void shouldLoadHighestPriorityPolicyByRequest() {
        PolicyRequest request = mock(DefaultPolicyRequest.class);
        Policies<Policy> policies = mock(Policies.class);
        given(load.load(request)).willReturn(policies);
        Policy expectedPolicy = mock(Policy.class);
        given(policies.getHighestPriority()).willReturn(expectedPolicy);

        Policy policy = service.loadHighestPriority(request);

        assertThat(policy).isEqualTo(expectedPolicy);
    }

    @Test
    void shouldThrowExceptionFromHandlerOnNoPoliciesFoundLoadHighestPriorityPolicyByRequest() {
        PolicyRequest request = mock(DefaultPolicyRequest.class);
        Policies<Policy> policies = mock(Policies.class);
        given(load.load(request)).willReturn(policies);
        given(policies.isEmpty()).willReturn(true);
        NoPoliciesConfiguredException expectedError = mock(NoPoliciesConfiguredException.class);
        given(handler.toException(request)).willReturn(expectedError);

        Throwable error = catchThrowable(() -> service.loadHighestPriority(request));

        assertThat(error).isEqualTo(expectedError);
    }

    @Test
    void shouldLoadPoliciesByKey() {
        PolicyKey key = mock(PolicyKey.class);
        Policies<Policy> expectedPolicies = mock(Policies.class);
        given(load.load(key)).willReturn(expectedPolicies);

        Policies<Policy> policies = service.load(key);

        assertThat(policies).isEqualTo(expectedPolicies);
    }

    @Test
    void shouldLoadAllPolicies() {
        Policies<Policy> expectedPolicies = mock(Policies.class);
        given(load.loadAll()).willReturn(expectedPolicies);

        Policies<Policy> policies = service.loadAll();

        assertThat(policies).isEqualTo(expectedPolicies);
    }

}
