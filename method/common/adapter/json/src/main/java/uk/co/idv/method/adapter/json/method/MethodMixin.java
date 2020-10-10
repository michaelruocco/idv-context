package uk.co.idv.method.adapter.json.method;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import uk.co.idv.method.entities.method.Method;
import uk.co.idv.method.entities.result.Results;

import java.time.Duration;
import java.util.Optional;

public interface MethodMixin {

    @JsonIgnore
    boolean isEligible();

    @JsonIgnore
    Duration getDuration();

    @JsonIgnore
    Optional<Method> getNext();

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    Results getResults();

    @JsonIgnore
    long getCompletedCount();

}
