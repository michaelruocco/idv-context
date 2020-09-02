package uk.co.idv.identity.usecases.eligibility.external.data;

import uk.co.idv.identity.entities.alias.Aliases;
import uk.co.idv.identity.entities.identity.FindIdentityRequest;

public interface AliasLoader {

    Aliases load(FindIdentityRequest request);

}
