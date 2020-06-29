package uk.co.idv.context.usecases.identity.save;

import uk.co.idv.context.entities.alias.IdvIdMother;

public interface CannotUpdateIdvIdExceptionMother {

    static CannotUpdateIdvIdException build() {
        return new CannotUpdateIdvIdException(
                IdvIdMother.idvId(),
                IdvIdMother.idvId1()
        );
    }

}
