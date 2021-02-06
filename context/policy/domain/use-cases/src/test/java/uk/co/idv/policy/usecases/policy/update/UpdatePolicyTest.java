package uk.co.idv.policy.usecases.policy.update;

import org.junit.jupiter.api.Test;
import uk.co.idv.policy.entities.policy.MockPolicyMother;
import uk.co.idv.policy.entities.policy.Policy;
import uk.co.idv.policy.entities.policy.PolicyNotFoundException;
import uk.co.idv.policy.usecases.policy.PolicyRepository;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class UpdatePolicyTest {

    private final PolicyRepository<Policy> repository = mock(PolicyRepository.class);

    private final UpdatePolicy<Policy> updatePolicy = new UpdatePolicy<>(repository);

    @Test
    void shouldThrowExceptionIfPolicyDoesNotExists() {
        UUID id = UUID.randomUUID();
        Policy policy = MockPolicyMother.withId(id);
        givenPolicyDoesNotExist(id);

        Throwable error = catchThrowable(() -> updatePolicy.update(policy));

        assertThat(error)
                .isInstanceOf(PolicyNotFoundException.class)
                .hasMessage(id.toString());
    }

    @Test
    void shouldSavePolicyIfPolicyAlreadyExists() {
        UUID id = UUID.randomUUID();
        Policy policy = MockPolicyMother.withId(id);
        givenPolicyAlreadyExists(policy);

        updatePolicy.update(policy);

        verify(repository).save(policy);
    }

    private void givenPolicyAlreadyExists(Policy policy) {
        given(repository.load(policy.getId())).willReturn(Optional.of(policy));
    }

    private void givenPolicyDoesNotExist(UUID id) {
        given(repository.load(id)).willReturn(Optional.empty());
    }

}
