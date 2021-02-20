package uk.co.idv.context.adapter.json.policy.sequence.stage.type;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface StageTypeMixin {

    @JsonProperty("type")
    String getName();

}
