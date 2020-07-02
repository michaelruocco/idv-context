package uk.co.idv.context.entities.alias;

import lombok.Getter;

@Getter
public class IdvIdAlreadyPresentException extends RuntimeException {

    private final IdvId existing;
    private final Alias updated;

    public IdvIdAlreadyPresentException(IdvId existing, Alias updated) {
        super("idvId already exists");
        this.existing = existing;
        this.updated = updated;
    }

}
