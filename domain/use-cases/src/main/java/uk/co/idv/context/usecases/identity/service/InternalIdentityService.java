package uk.co.idv.context.usecases.identity.service;

import lombok.Builder;
import uk.co.idv.context.entities.identity.Identity;
import uk.co.idv.context.usecases.identity.IdentityService;
import uk.co.idv.context.usecases.identity.FindIdentityRequest;
import uk.co.idv.context.usecases.identity.UpdateIdentityRequest;
import uk.co.idv.context.usecases.identity.service.find.InternalFindIdentity;
import uk.co.idv.context.usecases.identity.service.update.UpdateIdentity;

@Builder
public class InternalIdentityService implements IdentityService {

    private final UpdateIdentity update;
    private final InternalFindIdentity find;

    @Override
    public Identity update(UpdateIdentityRequest request) {
        return update.update(request.getIdentity());
    }

    @Override
    public Identity find(FindIdentityRequest request) {
        return find.find(request);
    }

}
