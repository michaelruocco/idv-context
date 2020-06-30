package uk.co.idv.context.adapter.json.error.identitynotfound;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import uk.co.idv.context.adapter.json.error.ApiError;
import uk.co.idv.context.entities.alias.Alias;
import uk.co.idv.context.entities.alias.Aliases;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Getter
public class IdentityNotFoundError implements ApiError {

    private static final int STATUS = 404;
    private static final String TITLE = "Identity not found";

    private final Aliases aliases;

    @Override
    public int getStatus() {
        return STATUS;
    }

    @Override
    public String getTitle() {
        return TITLE;
    }

    @Override
    public String getMessage() {
        return aliases.stream()
                .map(Alias::format)
                .collect(Collectors.joining(","));
    }

    @Override
    public Map<String, Object> getMeta() {
        return Collections.emptyMap();
    }

}
