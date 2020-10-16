package uk.co.idv.policy.adapter.json;

import com.fasterxml.jackson.annotation.JsonIgnore;
import uk.co.idv.policy.entities.policy.Policy;

public interface PoliciesMixin {

    @JsonIgnore
    boolean isEmpty();

    @JsonIgnore
    Policy getHighestPriority();
}
