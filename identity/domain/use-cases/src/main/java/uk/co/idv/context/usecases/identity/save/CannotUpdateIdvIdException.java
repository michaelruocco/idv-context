package uk.co.idv.context.usecases.identity.save;

import lombok.Getter;
import uk.co.idv.context.entities.alias.IdvId;

@Getter
public class CannotUpdateIdvIdException extends RuntimeException {

    private final IdvId updated;
    private final IdvId existing;

    public CannotUpdateIdvIdException(IdvId updated, IdvId existing) {
        super("cannot update idv id");
        this.updated = updated;
        this.existing = existing;
    }

}
