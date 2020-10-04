package uk.co.idv.context.adapter.json.context.method;

import com.fasterxml.jackson.annotation.JsonIgnore;
import uk.co.idv.context.entities.context.method.Methods;
import uk.co.idv.context.entities.context.method.otp.delivery.DeliveryMethod;
import uk.co.idv.method.entities.method.Method;

import java.time.Duration;
import java.util.Collection;
import java.util.Optional;

public interface MethodsMixin {

    @JsonIgnore
    Collection<DeliveryMethod> getValues();

    @JsonIgnore
    boolean isEligible();

    @JsonIgnore
    boolean isComplete();

    @JsonIgnore
    boolean isSuccessful();

    @JsonIgnore
    Duration getDuration();

    @JsonIgnore
    boolean isEmpty();

    @JsonIgnore
    Optional<Method> getNext();

    @JsonIgnore
    Methods getEligibleIncomplete();

}
