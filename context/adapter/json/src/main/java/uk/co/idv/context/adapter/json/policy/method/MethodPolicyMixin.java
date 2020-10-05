package uk.co.idv.context.adapter.json.policy.method;

import com.fasterxml.jackson.annotation.JsonIgnore;
import uk.co.idv.identity.entities.identity.RequestedData;

public interface MethodPolicyMixin {

    @JsonIgnore
    RequestedData getRequestedData();

}
