package uk.co.idv.context.adapter.json.error.notnextmethod;

import lombok.Getter;
import uk.co.idv.common.adapter.json.error.DefaultApiError;

import static java.util.Collections.emptyMap;

@Getter
public class NotNextMethodError extends DefaultApiError {

    private static final int STATUS = 422;
    private static final String TITLE = "Not next method";

    public NotNextMethodError(String message) {
        super(STATUS, TITLE, message, emptyMap());
    }

}
