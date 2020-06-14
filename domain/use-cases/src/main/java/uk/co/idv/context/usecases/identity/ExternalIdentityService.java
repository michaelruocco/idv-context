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

    public Identity upsert(UpsertIdentityRequest upsertRequest) {
        FindIdentityRequest findRequest = converter.toFindRequest(upsertRequest);
        Identity identity = find.find(findRequest);
        return update.update(identity);
    }

}
