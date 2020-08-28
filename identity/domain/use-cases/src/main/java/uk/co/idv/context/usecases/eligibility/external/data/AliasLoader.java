package uk.co.idv.context.usecases.eligibility.external.data;

import uk.co.idv.context.entities.alias.Aliases;
import uk.co.idv.context.entities.identity.FindIdentityRequest;

public interface AliasLoader {

    Aliases load(FindIdentityRequest request);

}
