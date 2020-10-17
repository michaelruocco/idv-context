package uk.co.idv.identity.usecases.identity.merge;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.identity.entities.alias.Aliases;
import uk.co.idv.identity.entities.event.MergeIdentitiesEvent;
import uk.co.idv.identity.entities.identity.Identities;
import uk.co.idv.identity.entities.identity.Identity;
import uk.co.idv.identity.usecases.identity.IdentityRepository;
import uk.co.idv.identity.usecases.identity.idvid.IdvIdAllocator;

@Builder
@Slf4j
public class MergeIdentities {

    private final IdvIdAllocator idvIdAllocator;
    private final IdentityRepository repository;
    private final MergeIdentitiesHandler handler;

    public Identity merge(Identity identity, Identities original) {
        Identity identityWithId = idvIdAllocator.allocateIfRequired(identity);
        Identity merged = doMerge(identityWithId, original);
        delete(original);
        Identity saved = save(merged);
        publishMerge(saved, original);
        return saved;
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
        repository.create(identity);
        return identity;
    }

    private void publishMerge(Identity merged, Identities original) {
        MergeIdentitiesEvent event = MergeIdentitiesEvent.builder()
                .merged(merged)
                .original(original)
                .build();
        handler.merged(event);
    }

}
