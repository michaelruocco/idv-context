package uk.co.idv.context.usecases.identity.merge;

import uk.co.idv.context.entities.identity.Identity;

import java.util.Collection;

public interface MergeIdentities {

    Identity merge(Identity identity, Collection<Identity> identities);

}
