package uk.co.idv.context.policy.key;

import uk.co.idv.context.policy.Policy;
import uk.co.idv.context.policy.PolicyRequest;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface PolicyDao<T extends Policy> {

    void save(final T policy);

    Optional<T> load(final UUID id);

    Collection<T> load(final PolicyRequest request);

    Collection<T> load();

}
