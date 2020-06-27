package uk.co.idv.context.usecases.identity.update;

import lombok.Builder;
import uk.co.idv.context.entities.identity.Identities;
import uk.co.idv.context.entities.identity.Identity;
import uk.co.idv.context.usecases.identity.IdentityRepository;
import uk.co.idv.context.usecases.identity.create.CreateIdentity;
import uk.co.idv.context.usecases.identity.merge.MergeIdentities;
import uk.co.idv.context.usecases.identity.save.ExternalSaveIdentity;
import uk.co.idv.context.usecases.identity.save.InternalSaveIdentity;
import uk.co.idv.context.usecases.identity.save.SaveIdentity;

@Builder
public class UpdateIdentity {

    private final CreateIdentity create;
    private final MergeIdentities merge;
    private final SaveIdentity save;
    private final IdentityRepository repository;

    public static UpdateIdentity buildInternal(IdentityRepository repository) {
        return build(repository, new InternalSaveIdentity(repository));
    }

    public static UpdateIdentity buildExternal(IdentityRepository repository) {
        return build(repository, new ExternalSaveIdentity(repository));
    }

    public static UpdateIdentity build(IdentityRepository repository, SaveIdentity save) {
        return UpdateIdentity.builder()
                .create(CreateIdentity.build(repository))
                .merge(MergeIdentities.build(repository))
                .save(save)
                .repository(repository)
                .build();
    }

    public Identity update(Identity identity) {
        return checkAgainstExistingIdentities(identity);
    }

    private Identity checkAgainstExistingIdentities(Identity identity) {
        Identities existing = repository.load(identity.getAliases());
        return handle(identity, existing);
    }

    private Identity handle(Identity identity, Identities existing) {
        switch (existing.size()) {
            case 0:
                return create.create(identity);
            case 1:
                return save.save(identity, existing.getFirst());
            default:
                return merge.merge(identity, existing);
        }
    }

}
