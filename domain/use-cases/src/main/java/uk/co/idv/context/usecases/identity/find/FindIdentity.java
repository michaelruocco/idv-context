package uk.co.idv.context.usecases.identity.find;

import uk.co.idv.context.entities.identity.Identity;
import uk.co.idv.context.usecases.identity.FindIdentityRequest;

public interface FindIdentity {

    Identity find(FindIdentityRequest request);

}
