package uk.co.idv.context.usecases.identity;

import uk.co.idv.context.entities.alias.Alias;

public class IdentityNotFoundException extends RuntimeException {

    private final Alias alias;

    public IdentityNotFoundException(Alias alias) {
        super(alias.format());
        this.alias = alias;
    }

    public Alias getAlias() {
        return alias;
    }

}
