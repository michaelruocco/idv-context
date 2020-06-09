package uk.co.idv.context.usecases.identity;

import uk.co.idv.context.entities.identity.Identity;

public interface IdentityLoader {

    Identity load(LoadIdentityRequest request);

}
