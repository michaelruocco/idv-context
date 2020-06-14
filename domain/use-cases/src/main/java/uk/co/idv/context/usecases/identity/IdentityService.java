package uk.co.idv.context.usecases.identity;

import uk.co.idv.context.entities.identity.Identity;

public interface IdentityService {

    Identity find(FindIdentityRequest request);

}
