package uk.co.idv.context.usecases.identity.save;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.context.entities.identity.Identity;
import uk.co.idv.context.usecases.identity.IdentityRepository;

@Slf4j
@RequiredArgsConstructor
public class InternalSaveIdentity implements SaveIdentity {

    private final IdentityRepository repository;

    @Override
    public Identity save(Identity update, Identity existing) {
        Identity save = setIdvIdIfRequired(update, existing);
        repository.save(save);
        return save;
    }

    private Identity setIdvIdIfRequired(Identity update, Identity existing) {
        if (update.hasIdvId()) {
            if (update.hasAlias(existing.getIdvId())) {
                return update;
            }
            throw new CannotUpdateIdvIdException(update.getIdvId(), existing.getIdvId());
        }
        return update.setIdvId(existing.getIdvId());
    }

}
