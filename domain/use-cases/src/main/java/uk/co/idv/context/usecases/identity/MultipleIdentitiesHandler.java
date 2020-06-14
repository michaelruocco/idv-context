package uk.co.idv.context.usecases.identity;

import lombok.Builder;
import uk.co.idv.context.entities.identity.Identity;
import uk.co.idv.context.usecases.identity.create.CreateIdentity;
import uk.co.idv.context.usecases.identity.merge.MergeIdentities;
import uk.co.idv.context.usecases.identity.update.UpdateIdentity;

import java.util.Collection;

@Builder
public class MultipleIdentitiesHandler {

    private final CreateIdentity create;
    private final UpdateIdentity update;
    private final MergeIdentities merge;

    public Identity handle(Identity identity, Collection<Identity> existingIdentities) {
        switch (existingIdentities.size()) {
            case 0:
                return create.create(identity);
            case 1:
                return update.update(identity);
            default:
                return merge.merge(identity, existingIdentities);
        }
    }

}
