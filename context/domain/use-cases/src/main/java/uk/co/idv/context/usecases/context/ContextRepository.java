package uk.co.idv.context.usecases.context;

import uk.co.idv.context.entities.context.Context;

import java.util.Optional;
import java.util.UUID;

public interface ContextRepository {

    void save(Context context);

    Optional<Context> load(UUID id);

}
