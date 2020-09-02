package uk.co.idv.context.config.identity;

import uk.co.idv.identity.usecases.identity.find.FindIdentity;

public interface FindIdentityProvider {

    FindIdentity provideFindIdentity();

}
