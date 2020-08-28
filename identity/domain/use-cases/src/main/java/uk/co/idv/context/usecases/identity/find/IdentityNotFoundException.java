package uk.co.idv.context.usecases.identity.find;

import uk.co.idv.context.entities.alias.Alias;
import uk.co.idv.context.entities.alias.Aliases;
import uk.co.idv.context.entities.alias.DefaultAliases;

import java.util.stream.Collectors;

public class IdentityNotFoundException extends RuntimeException {

    private final transient Aliases aliases;

    public IdentityNotFoundException(Alias alias) {
        this(new DefaultAliases(alias));
    }

    public IdentityNotFoundException(Aliases aliases) {
        super(toMessage(aliases));
        this.aliases = aliases;
    }

    public Aliases getAliases() {
        return aliases;
    }

    private static String toMessage(Aliases aliases) {
        return aliases.stream()
                .map(IdentityNotFoundException::format)
                .collect(Collectors.joining(","));
    }

    private static String format(Alias alias) {
        return String.format("%s|%s", alias.getType(), alias.getValue());
    }

}
