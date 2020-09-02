package uk.co.idv.policy.usecases.policy.update;

import lombok.RequiredArgsConstructor;
import uk.co.idv.policy.entities.policy.Policy;
import uk.co.idv.policy.usecases.policy.PolicyRepository;
import uk.co.idv.policy.usecases.policy.load.PolicyNotFoundException;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class UpdatePolicy<T extends Policy> {

    private final PolicyRepository<T> repository;

    public void update(T policy) {
        UUID id = policy.getId();
        Optional<T> loadedPolicy = repository.load(id);
        if (loadedPolicy.isEmpty()) {
            throw new PolicyNotFoundException(id);
        }
        repository.save(policy);
    }

}
