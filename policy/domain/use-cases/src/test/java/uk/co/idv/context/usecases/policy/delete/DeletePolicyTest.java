package uk.co.idv.context.usecases.policy.delete;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.policy.Policy;
import uk.co.idv.context.usecases.policy.PolicyRepository;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class DeletePolicyTest {

    private final PolicyRepository<Policy> repository = mock(PolicyRepository.class);

    private final DeletePolicy<Policy> deletePolicy = new DeletePolicy<>(repository);

    @Test
    void shouldDeleteAllPolicies() {
        deletePolicy.deleteAll();

        verify(repository).deleteAll();
    }


}
