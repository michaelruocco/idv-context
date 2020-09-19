package uk.co.idv.context.adapter.json.policy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import uk.co.idv.identity.entities.identity.RequestedData;

import java.util.UUID;

public interface ContextPolicyMixin {

    @JsonIgnore
    UUID getId();

    @JsonIgnore
    int getPriority();

    @JsonIgnore
    RequestedData getRequestedData();

}
