package uk.co.idv.context.usecases.identity;

import lombok.Builder;
import uk.co.idv.context.entities.identity.Identity;
import uk.co.idv.context.usecases.identity.find.FindIdentity;

@Builder
public class InternalIdentityService implements IdentityService {

    private final FindIdentity find;

    @Override
    public Identity find(FindIdentityRequest request) {
        return find.find(request);
    }

}
