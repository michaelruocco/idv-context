package uk.co.idv.context.entities.alias;

import lombok.Getter;

@Getter
public class IdvIdAlreadyPresentException extends RuntimeException {

    private final Alias idvIdToAdd;
    private final IdvId existingIdvId;

    public IdvIdAlreadyPresentException(Alias alias, IdvId existingIdvId) {
        super("idvId already exists");
        this.idvIdToAdd = alias;
        this.existingIdvId = existingIdvId;
    }

}
