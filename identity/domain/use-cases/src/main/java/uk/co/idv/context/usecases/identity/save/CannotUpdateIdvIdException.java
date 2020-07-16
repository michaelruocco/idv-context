package uk.co.idv.context.usecases.identity.save;

import lombok.Getter;
import uk.co.idv.context.entities.alias.IdvId;

@Getter
public class CannotUpdateIdvIdException extends RuntimeException {

    private final transient IdvId updated;
    private final transient IdvId existing;

    public CannotUpdateIdvIdException(IdvId updated, IdvId existing) {
        super("cannot update idv id");
        this.updated = updated;
        this.existing = existing;
    }

}
