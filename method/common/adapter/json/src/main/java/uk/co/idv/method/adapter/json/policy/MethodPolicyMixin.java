package uk.co.idv.method.adapter.json.policy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import uk.co.idv.identity.entities.identity.RequestedData;

import java.time.Duration;

public interface MethodPolicyMixin {

    @JsonIgnore
    RequestedData getRequestedData();

    @JsonIgnore
    Duration getDuration();

}
