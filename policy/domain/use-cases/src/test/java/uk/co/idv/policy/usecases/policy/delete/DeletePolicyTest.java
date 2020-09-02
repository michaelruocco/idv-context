package uk.co.idv.policy.usecases.policy.delete;

import org.junit.jupiter.api.Test;
import uk.co.idv.policy.entities.policy.Policy;
import uk.co.idv.policy.usecases.policy.PolicyRepository;

import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class DeletePolicyTest {

    private final PolicyRepository<Policy> repository = mock(PolicyRepository.class);

    private final DeletePolicy<Policy> deletePolicy = new DeletePolicy<>(repository);

    @Test
    void shouldDeletePolicy() {
        UUID id = UUID.randomUUID();

        deletePolicy.delete(id);

        verify(repository).delete(id);
    }


}
