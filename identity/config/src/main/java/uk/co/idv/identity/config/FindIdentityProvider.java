package uk.co.idv.identity.config;

import uk.co.idv.identity.usecases.identity.find.FindIdentity;

public interface FindIdentityProvider {

    FindIdentity provideFindIdentity();

}
