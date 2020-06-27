package uk.co.idv.context.usecases.identity.find;

import lombok.Getter;
import uk.co.idv.context.entities.alias.Aliases;
import uk.co.idv.context.entities.identity.Identities;

@Getter
public class MultipleIdentitiesFoundException extends RuntimeException {

    private final Aliases aliases;
    private final Identities identities;

    public MultipleIdentitiesFoundException(Aliases aliases, Identities identities) {
        super("multiple identities found");
        this.aliases = aliases;
        this.identities = identities;
    }

}
