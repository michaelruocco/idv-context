package uk.co.idv.identity.usecases.identity.merge;

import lombok.RequiredArgsConstructor;
import uk.co.idv.identity.entities.event.MergeIdentitiesEvent;

import java.util.Arrays;
import java.util.Collection;

@RequiredArgsConstructor
public class CompositeMergeIdentitiesHandler implements MergeIdentitiesHandler {

    private final Collection<MergeIdentitiesHandler> handlers;

    public CompositeMergeIdentitiesHandler(MergeIdentitiesHandler... handlers) {
        this(Arrays.asList(handlers));
    }

    @Override
    public void merged(MergeIdentitiesEvent event) {
        handlers.forEach(handler -> handler.merged(event));
    }

}
