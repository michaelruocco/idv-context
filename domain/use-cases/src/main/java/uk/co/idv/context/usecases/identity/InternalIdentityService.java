package uk.co.idv.context.usecases.identity;

import lombok.Builder;
import uk.co.idv.context.entities.identity.Identity;
import uk.co.idv.context.usecases.identity.find.FindIdentity;
import uk.co.idv.context.usecases.identity.update.UpdateIdentity;

@Builder
public class InternalIdentityService implements IdentityService {

    private final FindIdentity find;
    private final UpdateIdentity update;

    public Identity find(FindIdentityRequest request) {
        return find.find(request);
    }

    public void update(Identity identity) {
        update.update(identity);
    }

}
