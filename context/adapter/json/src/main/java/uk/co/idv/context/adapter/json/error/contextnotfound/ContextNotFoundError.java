package uk.co.idv.context.adapter.json.error.contextnotfound;

import lombok.Getter;
import uk.co.idv.common.adapter.json.error.DefaultApiError;

import static java.util.Collections.emptyMap;

@Getter
public class ContextNotFoundError extends DefaultApiError {

    private static final int STATUS = 404;
    private static final String TITLE = "Context not found";

    public ContextNotFoundError(String message) {
        super(STATUS, TITLE, message, emptyMap());
    }

}
