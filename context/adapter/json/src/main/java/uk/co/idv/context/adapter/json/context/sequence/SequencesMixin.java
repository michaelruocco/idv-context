package uk.co.idv.context.adapter.json.context.sequence;

import com.fasterxml.jackson.annotation.JsonIgnore;
import uk.co.idv.context.adapter.json.context.method.MethodsMixin;

public interface SequencesMixin extends MethodsMixin {

    @JsonIgnore
    long getCompletedMethodCount();

}
