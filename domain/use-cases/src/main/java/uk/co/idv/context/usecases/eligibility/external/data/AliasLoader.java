package uk.co.idv.context.usecases.eligibility.external.data;

import uk.co.idv.context.entities.alias.Aliases;
import uk.co.idv.context.usecases.eligibility.external.ExternalFindIdentityRequest;

public interface AliasLoader {

    Aliases load(ExternalFindIdentityRequest request);

}
