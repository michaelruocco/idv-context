package uk.co.idv.context.adapter.repository;

import uk.co.idv.context.entities.context.Context;
import uk.co.idv.context.usecases.context.ContextRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class InMemoryContextRepository implements ContextRepository {

    private final Map<UUID, Context> store = new HashMap<>();

    @Override
    public void save(Context context) {
        store.put(context.getId(), context);
    }

    @Override
    public Optional<Context> load(UUID id) {
        return Optional.ofNullable(store.get(id));
    }

}
