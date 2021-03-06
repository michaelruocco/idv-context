package uk.co.idv.policy.usecases.policy.load;

import org.junit.jupiter.api.Test;
import uk.co.idv.policy.entities.policy.MockPolicyMother;
import uk.co.idv.policy.entities.policy.Policies;
import uk.co.idv.policy.entities.policy.Policy;
import uk.co.idv.policy.entities.policy.PolicyNotFoundException;
import uk.co.idv.policy.entities.policy.key.PolicyKey;
import uk.co.idv.policy.entities.policy.PolicyRequest;
import uk.co.idv.policy.usecases.policy.PolicyRepository;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
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
        Policy expectedPolicy = MockPolicyMother.policy();
        given(repository.load(id)).willReturn(Optional.of(expectedPolicy));

        Policy policy = loadPolicy.load(id);

        assertThat(policy).isEqualTo(expectedPolicy);
    }

    @Test
    void shouldThrowExceptionIfPolicyNotFoundById() {
        UUID id = UUID.randomUUID();
        given(repository.load(id)).willReturn(Optional.empty());

        Throwable error = catchThrowable(() -> loadPolicy.load(id));

        assertThat(error)
                .isInstanceOf(PolicyNotFoundException.class)
                .hasMessage(id.toString());
    }

    @Test
    void shouldLoadAllPoliciesIfRequestIsEmpty() {
        Policies<Policy> allPolicies = mock(Policies.class);
        given(repository.loadAll()).willReturn(allPolicies);

        PolicyRequest request = givenEmptyPolicyRequest();

        Policies<Policy> policies = loadPolicy.load(request);

        assertThat(policies).isEqualTo(allPolicies);
    }

    @Test
    void shouldLoadPoliciesApplicableToRequestIfRequestIsNotEmpty() {
        Policies<Policy> allPolicies = mock(Policies.class);
        given(repository.loadAll()).willReturn(allPolicies);

        Policy policy = MockPolicyMother.policy();
        PolicyRequest request = givenPoliciesApplicableToRequest(allPolicies, policy);

        Policies<Policy> policies = loadPolicy.load(request);

        assertThat(policies).containsExactly(policy);
    }

    @Test
    void shouldLoadPoliciesApplicablePolicyKeyInPriorityOrder() {
        Policies<Policy> allPolicies = mock(Policies.class);
        given(repository.loadAll()).willReturn(allPolicies);

        Policy lowPriority = MockPolicyMother.withPriority(25);
        Policy highPriority = MockPolicyMother.withPriority(100);
        PolicyRequest request1 = givenPoliciesApplicableToRequest(allPolicies, lowPriority, highPriority);

        Policy mediumPriority = MockPolicyMother.withPriority(50);
        PolicyRequest request2 = givenPoliciesApplicableToRequest(allPolicies, mediumPriority);

        PolicyKey key = mock(PolicyKey.class);
        given(keyConverter.toPolicyRequests(key)).willReturn(Arrays.asList(request1, request2));

        Policies<Policy> policies = loadPolicy.load(key);

        assertThat(policies).containsExactly(
                highPriority,
                mediumPriority,
                lowPriority
        );
    }

    private PolicyRequest givenEmptyPolicyRequest() {
        PolicyRequest request = mock(PolicyRequest.class);
        given(request.isEmpty()).willReturn(true);
        return request;
    }

    private PolicyRequest givenPoliciesApplicableToRequest(Policies<Policy> allPolicies,
                                                           Policy... policies) {
        PolicyRequest request = mock(PolicyRequest.class);
        given(request.isEmpty()).willReturn(false);
        Policies<Policy> applicablePolicies = new Policies<>(policies);
        given(allPolicies.getApplicable(request)).willReturn(applicablePolicies);
        return request;
    }

}
