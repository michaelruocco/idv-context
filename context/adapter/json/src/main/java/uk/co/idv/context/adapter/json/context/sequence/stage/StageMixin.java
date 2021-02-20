package uk.co.idv.context.adapter.json.context.sequence.stage;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import uk.co.idv.context.entities.context.sequence.stage.Stage;
import uk.co.idv.context.entities.policy.sequence.stage.StageType;
import uk.co.idv.method.entities.eligibility.Eligibility;

import java.time.Duration;
import java.util.Collection;

public interface StageMixin {

    @JsonProperty("type")
    String getTypeName();

    @JsonIgnore
    StageType getType();

    @JsonIgnore
    Duration getDuration();

    @JsonIgnore
    Eligibility getEligibility();

    @JsonIgnore
    boolean isEligible();

    @JsonIgnore
    Collection<Stage> getIneligibleMethodNames();

}
