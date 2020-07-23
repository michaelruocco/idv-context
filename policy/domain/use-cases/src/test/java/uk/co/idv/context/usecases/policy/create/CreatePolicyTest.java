package uk.co.idv.context.usecases.policy.create;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.policy.Policy;
import uk.co.idv.context.usecases.policy.PolicyRepository;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class CreatePolicyTest {

    private final PolicyRepository<Policy> repository = mock(PolicyRepository.class);

    private final CreatePolicy<Policy> createPolicy = new CreatePolicy<>(repository);

    @Test
    void shouldSavePolicy() {
        Policy policy = mock(Policy.class);

        createPolicy.create(policy);

        verify(repository).save(policy);
    }

}
