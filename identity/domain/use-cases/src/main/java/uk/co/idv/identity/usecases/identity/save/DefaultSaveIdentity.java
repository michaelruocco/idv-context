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
        if (hasChanged(save, existing)) {
            repository.update(save, existing);
        }
        return save;
    }

    private boolean hasChanged(Identity save, Identity existing) {
        boolean changed = !save.equals(existing);
        log.info("identity changed {} {}", changed, save.getIdvIdValue());
        log.debug("updated {} existing {}", save, existing);
        return changed;
    }

}
