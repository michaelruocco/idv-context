package uk.co.idv.context.usecases.identity.merge;

import uk.co.idv.context.entities.identity.Identity;
import uk.co.idv.context.usecases.identity.MultipleIdentitiesFoundException;

import java.util.Collection;

public class ErrorOnMergeIdentities implements MergeIdentities {

    @Override
    public Identity merge(Identity identity, Collection<Identity> existingIdentities) {
        throw new MultipleIdentitiesFoundException(identity.getAliases(), existingIdentities);
    }

}
