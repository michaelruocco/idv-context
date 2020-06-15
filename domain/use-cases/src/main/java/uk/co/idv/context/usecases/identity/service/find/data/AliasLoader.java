package uk.co.idv.context.usecases.identity.service.find.data;

import uk.co.idv.context.entities.alias.Aliases;
import uk.co.idv.context.usecases.identity.service.find.FindIdentityRequest;

public interface AliasLoader {

    Aliases load(FindIdentityRequest request);

}
