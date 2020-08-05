package uk.co.idv.context.adapter.json.policy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import uk.co.idv.context.entities.policy.Policy;

public interface PoliciesMixin {

    @JsonIgnore
    boolean isEmpty();

    @JsonIgnore
    Policy getHighestPriority();
}
