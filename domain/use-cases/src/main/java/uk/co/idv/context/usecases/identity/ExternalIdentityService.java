package uk.co.idv.context.usecases.identity;

import lombok.Builder;
import uk.co.idv.context.entities.identity.Identity;
import uk.co.idv.context.usecases.identity.find.FindIdentity;
import uk.co.idv.context.usecases.identity.update.UpdateIdentity;

@Builder
public class ExternalIdentityService implements IdentityService {

    private final FindIdentity find;
    private final UpdateIdentity update;

    public Identity find(FindIdentityRequest request) {
        Identity identity = find.find(request);
        return update.update(identity);
    }

}
