package uk.co.idv.identity.usecases.identity.update;

import lombok.Builder;
import uk.co.idv.identity.entities.identity.Identities;
import uk.co.idv.identity.entities.identity.Identity;
import uk.co.idv.identity.usecases.identity.IdentityRepository;
import uk.co.idv.identity.usecases.identity.create.CreateIdentity;
import uk.co.idv.identity.usecases.identity.merge.MergeIdentities;
import uk.co.idv.identity.usecases.identity.save.DefaultSaveIdentity;
import uk.co.idv.identity.usecases.identity.save.SaveIdentity;
import uk.co.idv.identity.usecases.identity.save.external.ExternalSaveIdentityStrategy;
import uk.co.idv.identity.usecases.identity.save.internal.InternalSaveIdentityStrategy;

@Builder
public class UpdateIdentity {

    private final CreateIdentity create;
    private final MergeIdentities merge;
    private final SaveIdentity save;
    private final IdentityRepository repository;

    public static UpdateIdentity buildInternal(IdentityRepository repository) {
        return build(repository, new DefaultSaveIdentity(repository, new InternalSaveIdentityStrategy()));
    }

    public static UpdateIdentity buildExternal(IdentityRepository repository) {
        return build(repository, new DefaultSaveIdentity(repository, new ExternalSaveIdentityStrategy()));
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
