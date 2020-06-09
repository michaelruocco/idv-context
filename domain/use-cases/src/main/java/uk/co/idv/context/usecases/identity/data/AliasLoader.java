package uk.co.idv.context.usecases.identity.data;

import uk.co.idv.context.entities.alias.Aliases;
import uk.co.idv.context.usecases.identity.LoadIdentityRequest;

public interface AliasLoader {

    Aliases load(LoadIdentityRequest request);

}
