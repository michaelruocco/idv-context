package uk.co.idv.context.adapter.json.context.sequence;

import com.fasterxml.jackson.annotation.JsonIgnore;
import uk.co.idv.context.entities.context.sequence.Sequence;

import java.time.Duration;
import java.util.Collection;

public interface SequencesMixin {

    @JsonIgnore
    Collection<Sequence> getValues();

    @JsonIgnore
    boolean isEligible();

    @JsonIgnore
    boolean isComplete();

    @JsonIgnore
    boolean isSuccessful();

    @JsonIgnore
    Duration getDuration();

    @JsonIgnore
    long getCompletedCount();

    @JsonIgnore
    long getCompletedMethodCount();

}
