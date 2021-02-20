package uk.co.idv.context.usecases.context;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.context.entities.context.Context;
import uk.co.idv.context.entities.context.ContextNotFoundException;
import uk.co.idv.method.entities.method.Method;

import java.util.UUID;
import java.util.function.UnaryOperator;

@RequiredArgsConstructor
@Slf4j
public class ContextMethodUpdater {

    private final ContextRepository repository;

    public Context update(UUID contextId, UnaryOperator<Method> function) {
        return repository.load(contextId)
                .map(context -> update(context, function))
                .orElseThrow(() -> new ContextNotFoundException(contextId));
    }

    private Context update(Context context, UnaryOperator<Method> function) {
        Context updated = context.updateMethods(function);
        repository.save(updated);
        return updated;
    }

}
