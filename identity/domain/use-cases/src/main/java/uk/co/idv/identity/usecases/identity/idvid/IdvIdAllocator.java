package uk.co.idv.identity.usecases.identity.idvid;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.common.usecases.id.IdGenerator;
import uk.co.idv.identity.entities.alias.IdvId;
import uk.co.idv.identity.entities.identity.Identity;

@RequiredArgsConstructor
@Slf4j
public class IdvIdAllocator {

    private final IdvIdGenerator idvIdGenerator;

    public IdvIdAllocator(IdGenerator idGenerator) {
        this(new IdvIdGenerator(idGenerator));
    }

    public Identity allocateIfRequired(Identity identity) {
        if (identity.hasIdvId()) {
            log.debug("identity {} already has idvId, idvId not allocated", identity);
            return identity;
        }
        return allocateIdvId(identity);
    }

    private Identity allocateIdvId(Identity identity) {
        IdvId idvId = idvIdGenerator.generate();
        Identity identityWithId = identity.setIdvId(idvId);
        log.info("allocated idvId {} to identity {}", idvId, identityWithId);
        return identityWithId;
    }

}
