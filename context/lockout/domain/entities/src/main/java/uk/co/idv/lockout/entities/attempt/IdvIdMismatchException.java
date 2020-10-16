package uk.co.idv.lockout.entities.attempt;

import lombok.Getter;
import uk.co.idv.identity.entities.alias.IdvId;

import java.util.Arrays;
import java.util.Collection;

@Getter
public class IdvIdMismatchException extends RuntimeException {

    private final transient Collection<IdvId> idvIds;

    public IdvIdMismatchException(IdvId... idvIds) {
        this(Arrays.asList(idvIds));
    }

    public IdvIdMismatchException(Collection<IdvId> idvIds) {
        super("idv ids do not match");
        this.idvIds = idvIds;
    }

}
