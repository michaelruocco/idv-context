package uk.co.idv.context.usecases.identity.merge;

import uk.co.idv.context.entities.identity.Identity;

import java.util.Collection;

public class MergeIdentities {

    public Identity merge(Identity identity, Collection<Identity> existingIdentities) {
        throw new MultipleIdentitiesFoundException(identity.getAliases(), existingIdentities);
    }

}
