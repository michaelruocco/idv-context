package uk.co.idv.identity.usecases.identity.idvid;

import lombok.RequiredArgsConstructor;
import uk.co.idv.common.usecases.id.IdGenerator;
import uk.co.idv.identity.entities.alias.IdvId;

@RequiredArgsConstructor
public class IdvIdGenerator {

    private final IdGenerator idGenerator;

    public IdvId generate() {
        return new IdvId(idGenerator.generate());
    }

}
