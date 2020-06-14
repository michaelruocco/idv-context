package uk.co.idv.context.usecases.identity.merge;

import lombok.Getter;
import uk.co.idv.context.entities.identity.Identity;

import java.util.Collection;

@Getter
public class MultipleIdentitiesFoundException extends RuntimeException {

    private final Identity identity;
    private final Collection<Identity> existingIdentities;

    public MultipleIdentitiesFoundException(Identity identity, Collection<Identity> existingIdentities) {
        super("multiple identities found");
        this.identity = identity;
        this.existingIdentities = existingIdentities;
    }

}
