package uk.co.idv.identity.usecases.identity.save;

import uk.co.idv.identity.entities.alias.IdvIdMother;

public interface CannotUpdateIdvIdExceptionMother {

    static CannotUpdateIdvIdException build() {
        return new CannotUpdateIdvIdException(
                IdvIdMother.idvId(),
                IdvIdMother.idvId1()
        );
    }

}
