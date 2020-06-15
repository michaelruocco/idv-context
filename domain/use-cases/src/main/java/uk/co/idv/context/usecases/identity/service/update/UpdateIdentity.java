package uk.co.idv.context.usecases.identity.service.update;

import lombok.Builder;
import uk.co.idv.context.entities.identity.Identity;
import uk.co.idv.context.usecases.identity.IdentityRepository;
import uk.co.idv.context.usecases.identity.service.create.CreateIdentity;
import uk.co.idv.context.usecases.identity.service.merge.MergeIdentities;

import java.util.Collection;

@Builder
public class UpdateIdentity {

    private final CreateIdentity create;
    private final MergeIdentities merge;
    private final IdentityRepository repository;

    public Identity update(Identity identity) {
        return checkAgainstExistingIdentities(identity);
    }

    private Identity checkAgainstExistingIdentities(Identity identity) {
        Collection<Identity> existingIdentities = repository.load(identity.getAliases());
        return handle(identity, existingIdentities);
    }

    private Identity handle(Identity identity, Collection<Identity> existingIdentities) {
        switch (existingIdentities.size()) {
            case 0:
                return create.create(identity);
            case 1:
                return save(identity);
            default:
                return merge.merge(identity, existingIdentities);
        }
    }

    private Identity save(Identity identity) {
        repository.save(identity);
        return identity;
    }
}
