package uk.co.idv.context.entities.context;

import lombok.Builder;
import lombok.Data;
import lombok.With;
import uk.co.idv.context.entities.activity.Activity;
import uk.co.idv.context.entities.context.create.ServiceCreateContextRequest;
import uk.co.idv.context.entities.context.method.Methods;
import uk.co.idv.context.entities.context.sequence.Sequences;
import uk.co.idv.identity.entities.channel.Channel;
import uk.co.idv.identity.entities.identity.Identity;
import uk.co.idv.method.entities.method.Method;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;
import java.util.function.UnaryOperator;

@Builder
@Data
public class Context {

    private final UUID id;
    private final Instant created;
    private final Instant expiry;
    private final ServiceCreateContextRequest request;

    @With
    private final Sequences sequences;

    public Channel getChannel() {
        return request.getChannel();
    }

    public Activity getActivity() {
        return request.getActivity();
    }

    public Identity getIdentity() {
        return request.getIdentity();
    }

    public boolean isEligible() {
        return sequences.isEligible();
    }

    public boolean isComplete() {
        return sequences.isComplete();
    }

    public boolean isSuccessful() {
        return sequences.isSuccessful();
    }

    public Duration getDuration() {
        return sequences.getDuration();
    }

    public boolean hasExpired(Instant timestamp) {
        return timestamp.isAfter(expiry);
    }

    public boolean hasNextEligibleIncompleteMethods(String name) {
        return !getNextEligibleIncompleteMethods(name).isEmpty();
    }

    public Methods getNextEligibleIncompleteMethods(String name) {
        Methods methods = getNextMethods(name);
        if (methods.isEmpty()) {
            throw new NotNextMethodInSequenceException(name);
        }
        return methods.getEligibleIncomplete();
    }

    public Methods getNextMethods(String name) {
        return sequences.getMethodsIfNext(name);
    }

    public Context apply(UnaryOperator<Method> function) {
        return withSequences(sequences.apply(function));
    }

}
