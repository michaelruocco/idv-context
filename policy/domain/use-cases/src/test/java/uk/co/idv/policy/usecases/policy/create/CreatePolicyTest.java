package uk.co.idv.policy.usecases.policy.create;

import org.junit.jupiter.api.Test;
import uk.co.idv.policy.entities.policy.MockPolicyMother;
import uk.co.idv.policy.entities.policy.Policy;
import uk.co.idv.policy.usecases.policy.PolicyRepository;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class CreatePolicyTest {

    private final PolicyRepository<Policy> repository = mock(PolicyRepository.class);

    private final CreatePolicy<Policy> createPolicy = new CreatePolicy<>(repository);

    @Test
    void shouldSavePolicy() {
        Policy policy = MockPolicyMother.policy();

        createPolicy.create(policy);

        verify(repository).save(policy);
    }

}
