package uk.co.idv.policy.usecases.policy.create;

import lombok.RequiredArgsConstructor;
import uk.co.idv.policy.entities.policy.Policy;
import uk.co.idv.policy.usecases.policy.PolicyRepository;

@RequiredArgsConstructor
public class CreatePolicy<T extends Policy> {

    private final PolicyRepository<T> repository;

    public void create(final T policy) {
        repository.save(policy);
    }

}
