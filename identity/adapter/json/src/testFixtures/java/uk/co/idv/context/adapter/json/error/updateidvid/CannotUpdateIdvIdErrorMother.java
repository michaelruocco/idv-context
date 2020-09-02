package uk.co.idv.context.adapter.json.error.updateidvid;

import uk.co.idv.identity.entities.alias.IdvIdMother;

public interface CannotUpdateIdvIdErrorMother {

    static CannotUpdateIdvIdError cannotUpdateIdvIdError() {
        return CannotUpdateIdvIdError.builder()
                .existing(IdvIdMother.idvId())
                .updated(IdvIdMother.idvId1())
                .build();
    }

}
