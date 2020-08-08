package uk.co.idv.context.usecases.policy;

import uk.co.idv.context.entities.policy.Policies;
import uk.co.idv.context.entities.policy.Policy;

import java.util.Optional;
import java.util.UUID;

public interface PolicyRepository<T extends Policy> {

    void save(T policy);

    Optional<T> load(UUID id);

    Policies<T> loadAll();

    void delete(UUID id);

}
