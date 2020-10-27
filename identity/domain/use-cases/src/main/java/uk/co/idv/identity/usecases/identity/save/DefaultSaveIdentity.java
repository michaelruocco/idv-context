package uk.co.idv.identity.usecases.identity.save;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.identity.entities.identity.Identity;
import uk.co.idv.identity.usecases.identity.IdentityRepository;

@Slf4j
@RequiredArgsConstructor
public class DefaultSaveIdentity implements SaveIdentity {

    private final IdentityRepository repository;
    private final SaveIdentityStrategy saveStrategy;

    @Override
    public Identity save(Identity updated, Identity existing) {
        Identity save = saveStrategy.prepare(updated, existing);
        if (isUnchanged(save, existing)) {
            logUnchanged(save, existing);
            return save;
        }
        repository.update(save, existing);
        return save;
    }

    private boolean isUnchanged(Identity save, Identity existing) {
        return save.equals(existing);
    }

    private void logUnchanged(Identity save, Identity existing) {
        log.info("skipping identity update as identity is unchanged {}", existing.getIdvIdValue());
        log.debug("updated {} existing {}", save, existing);
    }

}
