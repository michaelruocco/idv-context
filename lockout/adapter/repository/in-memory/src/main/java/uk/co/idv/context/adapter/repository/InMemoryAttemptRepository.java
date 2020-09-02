package uk.co.idv.context.adapter.repository;

import uk.co.idv.identity.entities.alias.IdvId;
import uk.co.idv.context.entities.lockout.attempt.Attempts;
import uk.co.idv.context.usecases.lockout.attempt.AttemptRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryAttemptRepository implements AttemptRepository {

    private final Map<IdvId, Attempts> store = new HashMap<>();

    @Override
    public void save(Attempts attempts) {
        store.put(attempts.getIdvId(), attempts);
    }

    @Override
    public Optional<Attempts> load(IdvId idvId) {
        return Optional.ofNullable(store.get(idvId));
    }

}
