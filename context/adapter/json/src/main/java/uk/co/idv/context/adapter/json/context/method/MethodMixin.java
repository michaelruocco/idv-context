package uk.co.idv.context.adapter.json.context.method;

import com.fasterxml.jackson.annotation.JsonIgnore;
import uk.co.idv.context.entities.context.eligibility.Eligibility;

import java.time.Duration;

public interface MethodMixin {

    @JsonIgnore
    Eligibility getEligibility();

    @JsonIgnore
    Duration getDuration();

}
