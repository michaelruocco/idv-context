package uk.co.idv.context.usecases.policy.load;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.policy.Policies;
import uk.co.idv.context.entities.policy.Policy;
import uk.co.idv.context.entities.policy.PolicyKey;
import uk.co.idv.context.entities.policy.PolicyRequest;
import uk.co.idv.context.usecases.policy.PolicyRepository;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class LoadPolicyTest {

    private final PolicyRepository<Policy> repository = mock(PolicyRepository.class);
    private final PolicyKeyConverter keyConverter = mock(PolicyKeyConverter.class);

    private final LoadPolicy<Policy> loadPolicy = LoadPolicy.builder()
            .repository(repository)
            .keyConverter(keyConverter)
            .build();

    @Test
    void shouldLoadPolicyById() {
        UUID id = UUID.randomUUID();
        Policy expectedPolicy = mock(Policy.class);
        given(repository.load(id)).willReturn(Optional.of(expectedPolicy));

        Policy policy = loadPolicy.load(id);

        assertThat(policy).isEqualTo(expectedPolicy);
    }

    @Test
    void shouldLoadHighestPriorityPolicyApplicableToRequest() {
        Policies<Policy> allPolicies = mock(Policies.class);
        given(repository.loadAll()).willReturn(allPolicies);

        PolicyRequest request = mock(PolicyRequest.class);
        Policies<Policy> applicablePolicies = mock(Policies.class);
        given(allPolicies.getApplicable(request)).willReturn(applicablePolicies);

        Policy expected = mock(Policy.class);
        given(applicablePolicies.getHighestPriority()).willReturn(expected);

        Policy policy = loadPolicy.load(request);

        assertThat(policy).isEqualTo(expected);
    }

    @Test
    void shouldLoadPoliciesApplicablePolicyKey() {
        Policy policy = mock(Policy.class);

        Policies<Policy> allPolicies = mock(Policies.class);
        given(repository.loadAll()).willReturn(allPolicies);

        PolicyRequest request = mock(PolicyRequest.class);
        Policies<Policy> applicablePolicies = new Policies<>(policy);
        given(allPolicies.getApplicable(request)).willReturn(applicablePolicies);

        PolicyKey key = mock(PolicyKey.class);
        given(keyConverter.toPolicyRequests(key)).willReturn(Collections.singleton(request));

        Policies<Policy> policies = loadPolicy.load(key);

        assertThat(policies).containsExactly(policy);
    }

}
