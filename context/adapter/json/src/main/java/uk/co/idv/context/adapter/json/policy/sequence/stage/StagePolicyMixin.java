package uk.co.idv.context.adapter.json.policy.sequence.stage;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import uk.co.idv.context.entities.policy.sequence.stage.StageType;
import uk.co.idv.identity.entities.identity.RequestedData;

public interface StagePolicyMixin {

    @JsonProperty("type")
    String getTypeName();

    @JsonIgnore
    RequestedData getRequestedData();

    @JsonIgnore
    StageType getTypePolicy();

}
