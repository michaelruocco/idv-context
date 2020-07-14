package uk.co.idv.context.usecases.identity.save.internal;

import uk.co.idv.context.entities.identity.Identity;
import uk.co.idv.context.usecases.identity.save.CannotUpdateIdvIdException;
import uk.co.idv.context.usecases.identity.save.SaveIdentityStrategy;

public class InternalSaveIdentityStrategy implements SaveIdentityStrategy {

    @Override
    public Identity prepare(Identity updated, Identity existing) {
        return setIdvIdIfRequired(updated, existing);
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
