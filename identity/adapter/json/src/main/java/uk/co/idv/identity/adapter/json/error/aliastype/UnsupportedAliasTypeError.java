package uk.co.idv.identity.adapter.json.error.aliastype;

import lombok.Getter;
import uk.co.idv.common.adapter.json.error.DefaultApiError;

import static java.util.Collections.emptyMap;

@Getter
public class UnsupportedAliasTypeError extends DefaultApiError {

    private static final int STATUS = 422;
    private static final String TITLE = "Unsupported alias type";

    public UnsupportedAliasTypeError(String type) {
        super(STATUS, TITLE, type, emptyMap());
    }

}
