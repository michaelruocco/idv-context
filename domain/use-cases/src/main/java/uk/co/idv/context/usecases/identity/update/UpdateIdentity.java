package uk.co.idv.context.usecases.identity.update;

import uk.co.idv.context.entities.identity.Identity;

public interface UpdateIdentity {

    Identity update(UpdateIdentityRequest request);

}
