package uk.co.idv.policy.usecases.policy;

import uk.co.idv.policy.entities.policy.Policies;
import uk.co.idv.policy.entities.policy.Policy;

import java.util.Optional;
import java.util.UUID;

public interface PolicyRepository<T extends Policy> {

    void save(T policy);

    Optional<T> load(UUID id);

    Policies<T> loadAll();

    void delete(UUID id);

}
