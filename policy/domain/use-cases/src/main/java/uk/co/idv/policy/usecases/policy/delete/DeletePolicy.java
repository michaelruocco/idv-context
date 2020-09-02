package uk.co.idv.policy.usecases.policy.delete;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.policy.entities.policy.Policy;
import uk.co.idv.policy.usecases.policy.PolicyRepository;

import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
public class DeletePolicy<T extends Policy> {

    private final PolicyRepository<T> repository;

    public void delete(UUID id) {
        repository.delete(id);
    }

}
