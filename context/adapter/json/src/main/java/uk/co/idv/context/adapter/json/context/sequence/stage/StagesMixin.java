package uk.co.idv.context.adapter.json.context.sequence.stage;

import com.fasterxml.jackson.annotation.JsonIgnore;
import uk.co.idv.context.entities.context.sequence.stage.Stage;

import java.time.Duration;
import java.util.Collection;

public interface StagesMixin {

    @JsonIgnore
    Collection<Stage> getValues();

    @JsonIgnore
    boolean isEmpty();

    @JsonIgnore
    Duration getTotalDuration();

    @JsonIgnore
    Collection<Stage> getIneligibleMethodNames();

}
