package uk.co.idv.context.usecases.identity;

import lombok.Builder;
import uk.co.idv.context.entities.identity.Identity;
import uk.co.idv.context.usecases.identity.find.InternalFindIdentity;

@Builder
public class InternalIdentityService implements IdentityService {

    private final InternalFindIdentity finder;

    public Identity find(FindIdentityRequest request) {
        return finder.find(request);
    }

}
