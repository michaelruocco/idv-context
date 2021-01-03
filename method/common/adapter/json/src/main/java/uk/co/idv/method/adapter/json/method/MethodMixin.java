package uk.co.idv.method.adapter.json.method;

import com.fasterxml.jackson.annotation.JsonIgnore;
import uk.co.idv.method.entities.method.Method;

import java.time.Duration;
import java.util.Optional;

public interface MethodMixin {

    @JsonIgnore
    boolean isEligible();

    @JsonIgnore
    Duration getDuration();

    @JsonIgnore
    Optional<Method> getNext();

    @JsonIgnore
    long getCompletedCount();

}
