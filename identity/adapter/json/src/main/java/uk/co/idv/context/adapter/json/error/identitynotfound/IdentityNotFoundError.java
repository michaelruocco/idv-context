package uk.co.idv.context.adapter.json.error.identitynotfound;

import lombok.Getter;
import uk.co.idv.context.adapter.json.error.DefaultApiError;
import uk.co.idv.identity.entities.alias.Alias;
import uk.co.idv.identity.entities.alias.Aliases;

import java.util.stream.Collectors;

import static java.util.Collections.emptyMap;

@Getter
public class IdentityNotFoundError extends DefaultApiError {

    private static final int STATUS = 404;
    private static final String TITLE = "Identity not found";

    public IdentityNotFoundError(Aliases aliases) {
        super(STATUS, TITLE, toMessage(aliases), emptyMap());
    }

    private static String toMessage(Aliases aliases) {
        return aliases.stream()
                .map(Alias::format)
                .collect(Collectors.joining(","));
    }

}
