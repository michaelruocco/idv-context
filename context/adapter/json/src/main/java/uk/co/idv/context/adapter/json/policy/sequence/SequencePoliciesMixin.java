package uk.co.idv.context.adapter.json.policy.sequence;

import com.fasterxml.jackson.annotation.JsonIgnore;
import uk.co.idv.identity.entities.identity.RequestedData;

public interface SequencePoliciesMixin {

    @JsonIgnore
    RequestedData getRequestedData();

}
