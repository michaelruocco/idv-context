package uk.co.idv.identity.usecases.identity.merge;

import uk.co.idv.identity.entities.event.MergeIdentitiesEvent;

public interface MergeIdentitiesHandler {

    void merged(MergeIdentitiesEvent event);

}
