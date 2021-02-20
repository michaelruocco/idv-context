package uk.co.idv.context.adapter.json.policy.sequence.stage;

import com.fasterxml.jackson.annotation.JsonIgnore;
import uk.co.idv.identity.entities.identity.RequestedData;

public interface StagePoliciesMixin {

    @JsonIgnore
    RequestedData getRequestedData();

}
