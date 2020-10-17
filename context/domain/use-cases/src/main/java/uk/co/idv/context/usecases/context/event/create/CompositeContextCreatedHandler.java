package uk.co.idv.context.usecases.context.event.create;

import lombok.RequiredArgsConstructor;
import uk.co.idv.context.entities.context.Context;

import java.util.Arrays;
import java.util.Collection;

@RequiredArgsConstructor
public class CompositeContextCreatedHandler implements ContextCreatedHandler {

    private final Collection<ContextCreatedHandler> publishers;

    public CompositeContextCreatedHandler(ContextCreatedHandler... publishers) {
        this(Arrays.asList(publishers));
    }

    public void created(Context context) {
        publishers.forEach(publisher -> publisher.created(context));
    }

}
