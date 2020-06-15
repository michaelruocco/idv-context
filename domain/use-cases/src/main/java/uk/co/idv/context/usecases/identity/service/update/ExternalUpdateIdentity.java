package uk.co.idv.context.usecases.identity.service.update;

import lombok.Builder;
import uk.co.idv.context.entities.identity.Identity;
import uk.co.idv.context.usecases.identity.request.ExternalFindIdentityRequest;
import uk.co.idv.context.usecases.identity.service.UpdateIdentityRequestConverter;
import uk.co.idv.context.usecases.identity.service.find.ExternalFindIdentity;

@Builder
public class ExternalUpdateIdentity implements UpdateIdentity {

    @Builder.Default
    private final UpdateIdentityRequestConverter converter = new UpdateIdentityRequestConverter();

    private final ExternalFindIdentity externalFind;
    private final UpdateIdentity update;

    @Override
    public Identity update(UpdateIdentityRequest updateRequest) {
        ExternalFindIdentityRequest findRequest = converter.toFindRequest(updateRequest);
        Identity identity = externalFind.find(findRequest);
        return update.update(updateRequest.setIdentity(identity));
    }

}
