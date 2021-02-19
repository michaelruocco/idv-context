package uk.co.idv.context.usecases.context;

import uk.co.idv.context.entities.context.Context;

import java.util.UUID;
import java.util.function.Function;

public interface ContextFinder extends Function<UUID, Context> {

    default Context apply(UUID id) {
        return find(id);
    }

    Context find(UUID id);

}
