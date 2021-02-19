package uk.co.idv.context.usecases.context.method;

import lombok.Builder;
import uk.co.idv.context.entities.context.Context;
import uk.co.idv.method.entities.method.Method;

import java.util.UUID;
import java.util.function.Function;
import java.util.function.Supplier;

@Builder
public class NextMethodsSupplier implements Supplier<Iterable<Method>> {

    private final Function<UUID, Context> contextFinder;
    private final UUID contextId;

    @Override
    public Iterable<Method> get() {
        Context context = contextFinder.apply(contextId);
        return context.getNextMethods();
    }

}
