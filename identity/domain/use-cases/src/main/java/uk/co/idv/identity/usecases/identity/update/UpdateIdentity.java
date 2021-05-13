package uk.co.idv.identity.usecases.identity.update;

import lombok.Builder;
import uk.co.idv.identity.entities.identity.Identities;
import uk.co.idv.identity.entities.identity.Identity;
import uk.co.idv.identity.usecases.identity.IdentityRepository;
import uk.co.idv.identity.usecases.identity.create.CreateIdentity;
import uk.co.idv.identity.usecases.identity.merge.MergeIdentities;
import uk.co.idv.identity.usecases.identity.save.SaveIdentity;

@Builder
public class UpdateIdentity {

    private final CreateIdentity create;
    private final MergeIdentities merge;
    private final SaveIdentity save;
    private final IdentityRepository repository;

    public Identity update(Identity identity) {
        return checkAgainstExistingIdentities(identity);
    }

    private Identity checkAgainstExistingIdentities(Identity identity) {
        Identities existing = repository.load(identity.getAliases());
        return handle(identity, existing);
    }

    private Identity handle(Identity identity, Identities existing) {
        return switch (existing.size()) {
            case 0 -> create.create(identity);
            case 1 -> save.save(identity, existing.getFirst());
            default -> merge.merge(identity, existing);
        };
    }

}
