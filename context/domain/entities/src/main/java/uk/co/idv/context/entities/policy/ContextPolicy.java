package uk.co.idv.context.entities.policy;

import uk.co.idv.context.entities.identity.RequestedData;

import java.util.Collections;

public class ContextPolicy {

    public RequestedData getRequestedData() {
        return new RequestedData(Collections.singleton("phone-numbers"));
    }

}
