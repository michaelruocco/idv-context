package uk.co.idv.policy.adapter.repository;

import org.junit.jupiter.api.Test;
import uk.co.idv.policy.entities.policy.MockPolicyMother;
import uk.co.idv.policy.entities.policy.Policies;
import uk.co.idv.policy.entities.policy.Policy;
import uk.co.idv.policy.usecases.policy.PolicyRepository;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class InMemoryPolicyRepositoryTest {

    private final PolicyRepository<Policy> repository = new InMemoryPolicyRepository<>();

    @Test
    void shouldReturnEmptyOptionalIfPolicyNotFoundById() {
        UUID id = UUID.randomUUID();

        assertThat(repository.load(id)).isEmpty();
    }

    @Test
    void shouldLoadSavedPolicyById() {
        UUID id = UUID.randomUUID();
        Policy policy = MockPolicyMother.withId(id);
        repository.save(policy);

        Optional<Policy> loadedPolicy = repository.load(id);

        assertThat(loadedPolicy).contains(policy);
    }

    @Test
    void shouldLoadAllSavedPolicies() {
        Policy policy1 = MockPolicyMother.withId(UUID.randomUUID());
        Policy policy2 = MockPolicyMother.withId(UUID.randomUUID());
        repository.save(policy1);
        repository.save(policy2);

        Policies<Policy> policies = repository.loadAll();

        assertThat(policies).containsExactlyInAnyOrder(policy1, policy2);
    }

    @Test
    void shouldDeletePolicyById() {
        Policy policy1 = MockPolicyMother.withId(UUID.randomUUID());
        Policy policy2 = MockPolicyMother.withId(UUID.randomUUID());
        repository.save(policy1);
        repository.save(policy2);

        repository.delete(policy1.getId());

        Policies<Policy> policies = repository.loadAll();

        assertThat(policies).containsExactly(policy2);
    }

}
