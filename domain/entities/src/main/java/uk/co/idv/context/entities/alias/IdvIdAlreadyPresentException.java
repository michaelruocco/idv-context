package uk.co.idv.context.entities.alias;

import lombok.Getter;

@Getter
public class IdvIdAlreadyPresentException extends RuntimeException {

    private final IdvId existingIdvId;
    private final Alias idvIdToAdd;

    public IdvIdAlreadyPresentException(IdvId existingIdvId, Alias idvIdToAdd) {
        super("idvId already exists");
        this.existingIdvId = existingIdvId;
        this.idvIdToAdd = idvIdToAdd;
    }

}
