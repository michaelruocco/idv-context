package uk.co.idv.context.usecases.identity;

import lombok.Builder;
import uk.co.idv.context.entities.identity.Identity;
import uk.co.idv.context.usecases.identity.find.FindIdentity;
import uk.co.idv.context.usecases.identity.update.UpdateIdentity;

@Builder
public class InternalIdentityService implements IdentityService {

    private final UpdateIdentity update;
    private final FindIdentity find;

    @Override
    public Identity update(UpdateIdentityRequest request) {
        return update.update(request.getIdentity());
    }

    @Override
    public Identity find(FindIdentityRequest request) {
        return find.find(request);
    }

}
