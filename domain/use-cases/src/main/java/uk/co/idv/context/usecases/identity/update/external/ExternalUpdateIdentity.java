package uk.co.idv.context.usecases.identity.update.external;

import lombok.Builder;
import uk.co.idv.context.entities.identity.Identity;
import uk.co.idv.context.usecases.identity.find.external.ExternalFindIdentityRequest;
import uk.co.idv.context.usecases.identity.UpdateIdentityRequestConverter;
import uk.co.idv.context.usecases.identity.find.external.ExternalFindIdentity;
import uk.co.idv.context.usecases.identity.update.UpdateIdentity;
import uk.co.idv.context.usecases.identity.update.UpdateIdentityRequest;

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
