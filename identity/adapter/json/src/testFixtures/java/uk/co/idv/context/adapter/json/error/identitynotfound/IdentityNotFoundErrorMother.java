package uk.co.idv.context.adapter.json.error.identitynotfound;

import uk.co.idv.context.entities.alias.Alias;
import uk.co.idv.context.entities.alias.Aliases;
import uk.co.idv.context.entities.alias.AliasesMother;
import uk.co.idv.context.entities.alias.IdvIdMother;

public interface IdentityNotFoundErrorMother {

    static IdentityNotFoundError identityNotFoundError() {
        return identityNotFoundError(IdvIdMother.idvId());
    }

    static IdentityNotFoundError identityNotFoundError(Alias... aliases) {
        return identityNotFoundError(AliasesMother.with(aliases));
    }

    static IdentityNotFoundError identityNotFoundError(Aliases aliases) {
        return new IdentityNotFoundError(aliases);
    }

}