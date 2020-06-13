package uk.co.idv.context.usecases.identity;

import lombok.Builder;
import uk.co.idv.context.entities.identity.Identity;
import uk.co.idv.context.usecases.identity.create.CreateIdentity;
import uk.co.idv.context.usecases.identity.find.ExternalFindIdentity;
import uk.co.idv.context.usecases.identity.merge.MergeIdentities;
import uk.co.idv.context.usecases.identity.update.IdentityUpdate;

import java.util.Collection;

@Builder
public class ExternalIdentityService implements IdentityService {

    private final ExternalFindIdentity finder;
    private final MergeIdentities merger;
    private final IdentityUpdate updater;
    private final CreateIdentity creator;
    private final IdentityRepository repository;

    public Identity find(FindIdentityRequest request) {
        Identity identity = finder.find(request);
        Collection<Identity> identities = repository.load(identity.getAliases());
        return handle(identity, identities);
    }

    private Identity handle(Identity identity, Collection<Identity> identities) {
        switch (identities.size()) {
            case 0: return creator.create(identity);
            case 1: return updater.update(identity);
            default: return merger.merge(identity, identities);
        }
    }

}
