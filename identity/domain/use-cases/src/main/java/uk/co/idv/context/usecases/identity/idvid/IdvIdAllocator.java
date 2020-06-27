package uk.co.idv.context.usecases.identity.idvid;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.context.entities.alias.IdvId;
import uk.co.idv.context.entities.identity.Identity;

@RequiredArgsConstructor
@Slf4j
public class IdvIdAllocator {

    private final RandomIdvIdGenerator idGenerator;

    public IdvIdAllocator() {
        this(new RandomIdvIdGenerator());
    }

    public Identity allocateIfRequired(Identity identity) {
        if (identity.hasIdvId()) {
            log.debug("identity {} already has idvId, idvId not allocated", identity);
            return identity;
        }
        return allocateIdvId(identity);
    }

    private Identity allocateIdvId(Identity identity) {
        IdvId idvId = idGenerator.generate();
        Identity identityWithId = identity.setIdvId(idvId);
        log.info("allocated idvId {} to identity {}", idvId, identityWithId);
        return identityWithId;
    }

}
