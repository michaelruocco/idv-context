package uk.co.idv.context.usecases.identity;

import lombok.Getter;
import uk.co.idv.context.entities.alias.Aliases;
import uk.co.idv.context.entities.identity.Identity;

import java.util.Collection;

@Getter
public class MultipleIdentitiesFoundException extends RuntimeException {

    private final Aliases aliases;
    private final Collection<Identity> existingIdentities;

    public MultipleIdentitiesFoundException(Aliases aliases, Collection<Identity> existingIdentities) {
        super("multiple identities found");
        this.aliases = aliases;
        this.existingIdentities = existingIdentities;
    }

}
