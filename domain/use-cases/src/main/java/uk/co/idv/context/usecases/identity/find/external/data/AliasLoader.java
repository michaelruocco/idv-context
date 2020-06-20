package uk.co.idv.context.usecases.identity.find.external.data;

import uk.co.idv.context.entities.alias.Aliases;
import uk.co.idv.context.usecases.identity.find.external.ExternalFindIdentityRequest;

public interface AliasLoader {

    Aliases load(ExternalFindIdentityRequest request);

}
