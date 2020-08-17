package uk.co.idv.context.config.identity;

import uk.co.idv.context.usecases.identity.find.FindIdentity;

public interface FindIdentityProvider {

    FindIdentity provideFindIdentity();

}
