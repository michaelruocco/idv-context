package uk.co.idv.context.usecases.identity;

import lombok.Builder;
import uk.co.idv.context.entities.identity.Identity;
import uk.co.idv.context.usecases.identity.update.UpdateIdentity;

@Builder
public class InternalIdentityService implements IdentityService {

    private final UpdateIdentity update;

    @Override
    public Identity upsert(UpsertIdentityRequest request) {
        return update.update(request.getIdentity());
    }

}
