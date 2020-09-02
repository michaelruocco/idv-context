package uk.co.idv.identity.usecases.identity.idvid;

import uk.co.idv.identity.entities.alias.IdvId;

public class RandomIdvIdGenerator {

    public IdvId generate() {
        return IdvId.random();
    }

}
