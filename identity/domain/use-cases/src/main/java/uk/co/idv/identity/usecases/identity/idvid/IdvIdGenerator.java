package uk.co.idv.identity.usecases.identity.idvid;

import lombok.RequiredArgsConstructor;
import uk.co.idv.identity.entities.alias.IdvId;
import uk.co.mruoc.randomvalue.uuid.UuidGenerator;

@RequiredArgsConstructor
public class IdvIdGenerator {

    private final UuidGenerator uuidGenerator;

    public IdvId generate() {
        return new IdvId(uuidGenerator.generate());
    }

}
