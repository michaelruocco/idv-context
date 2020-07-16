package uk.co.idv.context.usecases.identity.find;

import lombok.Getter;
import uk.co.idv.context.entities.alias.Aliases;
import uk.co.idv.context.entities.identity.Identities;

@Getter
public class MultipleIdentitiesFoundException extends RuntimeException {

    private final transient Aliases aliases;
    private final transient Identities identities;

    public MultipleIdentitiesFoundException(Aliases aliases, Identities identities) {
        super("multiple identities found");
        this.aliases = aliases;
        this.identities = identities;
    }

}
