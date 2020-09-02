package uk.co.idv.policy.adapter.json.error.policynotfound;

import lombok.Getter;
import uk.co.idv.common.adapter.json.error.DefaultApiError;

import static java.util.Collections.emptyMap;

@Getter
public class PolicyNotFoundError extends DefaultApiError {

    private static final int STATUS = 404;
    private static final String TITLE = "Policy not found";

    public PolicyNotFoundError(String message) {
        super(STATUS, TITLE, message, emptyMap());
    }

}
