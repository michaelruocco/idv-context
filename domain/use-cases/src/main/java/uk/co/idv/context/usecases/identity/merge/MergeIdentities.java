package uk.co.idv.context.usecases.identity.merge;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.context.entities.alias.Aliases;
import uk.co.idv.context.entities.identity.Identities;
import uk.co.idv.context.entities.identity.Identity;
import uk.co.idv.context.usecases.identity.IdentityRepository;
import uk.co.idv.context.usecases.identity.idvid.IdvIdAllocator;

@Builder
@Slf4j
public class MergeIdentities {

    @Builder.Default
    private final IdvIdAllocator idvIdAllocator = new IdvIdAllocator();
    private final IdentityRepository repository;

    public static MergeIdentities build(IdentityRepository repository) {
        return MergeIdentities.builder()
                .repository(repository)
                .build();
    }

    public Identity merge(Identity identity, Identities existing) {
        Identity identityWithId = idvIdAllocator.allocateIfRequired(identity);
        delete(existing);
        Identity merged = doMerge(identityWithId, existing);
        return save(merged);
    }

    private void delete(Identities existing) {
        Aliases idvIdsToDelete = existing.getIdvIds();
        log.debug("deleting identities with idvIds {}", idvIdsToDelete);
        repository.delete(idvIdsToDelete);
    }

    private Identity doMerge(Identity identity, Identities existing) {
        log.debug("merging {} with existing {}", identity, existing);
        return identity.addData(existing);
    }

    private Identity save(Identity identity) {
        log.debug("saving merged identity {}", identity);
        repository.save(identity);
        return identity;
    }

}
