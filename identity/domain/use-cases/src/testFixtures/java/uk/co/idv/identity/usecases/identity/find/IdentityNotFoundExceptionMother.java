package uk.co.idv.identity.usecases.identity.find;

import uk.co.idv.identity.entities.alias.AliasesMother;

public interface IdentityNotFoundExceptionMother {

    static IdentityNotFoundException build() {
        return new IdentityNotFoundException(AliasesMother.idvIdOnly());
    }

}
