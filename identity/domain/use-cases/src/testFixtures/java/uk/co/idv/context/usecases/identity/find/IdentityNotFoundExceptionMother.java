package uk.co.idv.context.usecases.identity.find;

import uk.co.idv.context.entities.alias.AliasesMother;

public interface IdentityNotFoundExceptionMother {

    static IdentityNotFoundException build() {
        return new IdentityNotFoundException(AliasesMother.idvIdOnly());
    }

}
