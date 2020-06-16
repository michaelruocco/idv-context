package uk.co.idv.context.usecases.identity.create;

import uk.co.idv.context.entities.alias.IdvId;

public class RandomIdvIdGenerator {

    public IdvId generate() {
        return IdvId.random();
    }

}
