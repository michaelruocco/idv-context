package uk.co.idv.context.usecases.policy.delete;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.context.entities.policy.Policy;
import uk.co.idv.context.usecases.policy.PolicyRepository;

@RequiredArgsConstructor
@Slf4j
public class DeletePolicy<T extends Policy> {

    private final PolicyRepository<T> repository;

    public void deleteAll() {
        repository.deleteAll();
    }

}
