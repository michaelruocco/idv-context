package uk.co.idv.method.adapter.json.method;

import com.fasterxml.jackson.annotation.JsonIgnore;
import uk.co.idv.method.entities.method.Method;

import java.time.Duration;
import java.util.Collection;

public interface MethodsMixin {

    @JsonIgnore
    Collection<Method> getValues();

    @JsonIgnore
    boolean isEmpty();

    @JsonIgnore
    Duration getLongestDuration();

    @JsonIgnore
    Duration getShortestDuration();

    @JsonIgnore
    Duration getTotalDuration();

    @JsonIgnore
    Collection<String> getIneligibleNames();

}
