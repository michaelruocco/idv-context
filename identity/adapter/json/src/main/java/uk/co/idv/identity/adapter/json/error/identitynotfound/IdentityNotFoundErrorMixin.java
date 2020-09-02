package uk.co.idv.identity.adapter.json.error.identitynotfound;

import com.fasterxml.jackson.annotation.JsonIgnore;
import uk.co.idv.identity.entities.alias.Aliases;


public interface IdentityNotFoundErrorMixin {

    @JsonIgnore
    Aliases getAliases();

}
