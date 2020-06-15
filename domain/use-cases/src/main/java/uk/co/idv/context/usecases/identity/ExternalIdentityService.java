package uk.co.idv.context.usecases.identity;

import lombok.Builder;
import uk.co.idv.context.entities.identity.Identity;
import uk.co.idv.context.usecases.identity.find.FindIdentity;
import uk.co.idv.context.usecases.identity.update.UpdateIdentity;

@Builder
public class ExternalIdentityService implements IdentityService {

    private final UpsertIdentityRequestConverter converter;
    private final FindIdentity find;
    private final UpdateIdentity update;

    @Override
    public Identity update(UpdateIdentityRequest upsertRequest) {
        FindIdentityRequest findRequest = converter.toFindRequest(upsertRequest);
        Identity identity = find(findRequest);
        return update.update(identity);
    }

    @Override
    public Identity find(FindIdentityRequest request) {
        return find.find(request);
    }

}
