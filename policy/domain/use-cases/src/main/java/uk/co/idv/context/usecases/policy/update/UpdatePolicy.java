package uk.co.idv.context.usecases.policy.update;

import lombok.RequiredArgsConstructor;
import uk.co.idv.context.entities.policy.Policy;
import uk.co.idv.context.usecases.policy.PolicyRepository;
import uk.co.idv.context.usecases.policy.load.PolicyNotFoundException;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class UpdatePolicy<T extends Policy> {

    private final PolicyRepository<T> repository;

    public void update(final T policy) {
        final UUID id = policy.getId();
        final Optional<T> loadedPolicy = repository.load(id);
        if (loadedPolicy.isEmpty()) {
            throw new PolicyNotFoundException(id);
        }
        repository.save(policy);
    }

}
