package uk.co.idv.context.usecases.policy;

import uk.co.idv.context.entities.policy.Policies;
import uk.co.idv.context.entities.policy.Policy;

import java.util.Optional;
import java.util.UUID;

public interface PolicyRepository<T extends Policy> {

    void save(final T policy);

    Optional<T> load(final UUID id);

    Policies<T> loadAll();

}
