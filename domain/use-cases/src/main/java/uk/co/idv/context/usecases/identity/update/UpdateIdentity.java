package uk.co.idv.context.usecases.identity.update;

import lombok.Builder;
import org.apache.commons.collections4.IterableUtils;
import uk.co.idv.context.entities.identity.Identity;
import uk.co.idv.context.usecases.identity.IdentityRepository;
import uk.co.idv.context.usecases.identity.create.CreateIdentity;
import uk.co.idv.context.usecases.identity.merge.MergeIdentities;
import uk.co.idv.context.usecases.identity.save.ExternalSaveIdentity;
import uk.co.idv.context.usecases.identity.save.InternalSaveIdentity;
import uk.co.idv.context.usecases.identity.save.SaveIdentity;

import java.util.Collection;

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
                .save(save)
                .merge(new MergeIdentities())
                .repository(repository)
                .build();
    }

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
                return save.save(identity, IterableUtils.get(existingIdentities, 0));
            default:
                return merge.merge(identity, existingIdentities);
        }
    }

}
