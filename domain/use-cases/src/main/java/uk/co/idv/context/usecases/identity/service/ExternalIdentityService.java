package uk.co.idv.context.usecases.identity.service;

import lombok.Builder;
import uk.co.idv.context.entities.identity.Identity;
import uk.co.idv.context.usecases.identity.IdentityService;
import uk.co.idv.context.usecases.identity.service.find.ExternalFindIdentity;
import uk.co.idv.context.usecases.identity.service.find.InternalFindIdentity;
import uk.co.idv.context.usecases.identity.request.ExternalFindIdentityRequest;
import uk.co.idv.context.usecases.identity.FindIdentityRequest;
import uk.co.idv.context.usecases.identity.UpdateIdentityRequest;
import uk.co.idv.context.usecases.identity.service.update.UpdateIdentity;

@Builder
public class ExternalIdentityService implements IdentityService {

    private final UpdateIdentityRequestConverter converter;
    private final ExternalFindIdentity externalFind;
    private final InternalFindIdentity internalFind;
    private final UpdateIdentity update;

    @Override
    public Identity update(UpdateIdentityRequest upsertRequest) {
        ExternalFindIdentityRequest findRequest = converter.toFindRequest(upsertRequest);
        Identity identity = externalFind.find(findRequest);
        return update.update(identity);
    }

    @Override
    public Identity find(FindIdentityRequest request) {
        return internalFind.find(request);
    }

}
