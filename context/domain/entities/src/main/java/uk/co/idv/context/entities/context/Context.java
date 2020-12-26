package uk.co.idv.context.entities.context;

import lombok.Builder;
import lombok.Data;
import lombok.With;
import uk.co.idv.context.entities.activity.Activity;
import uk.co.idv.context.entities.context.create.ServiceCreateContextRequest;
import uk.co.idv.context.entities.context.method.Methods;
import uk.co.idv.context.entities.context.sequence.Sequences;
import uk.co.idv.identity.entities.alias.Aliases;
import uk.co.idv.identity.entities.alias.IdvId;
import uk.co.idv.identity.entities.channel.Channel;
import uk.co.idv.identity.entities.identity.Identity;
import uk.co.idv.method.entities.method.Method;
import uk.co.idv.method.entities.sequence.MethodSequence;

import java.time.Duration;
import java.time.Instant;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

@Builder(toBuilder = true)
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

    public String getChannelId() {
        return request.getChannelId();
    }

    public Activity getActivity() {
        return request.getActivity();
    }

    public String getActivityName() {
        return request.getActivityName();
    }

    public Identity getIdentity() {
        return request.getIdentity();
    }

    public IdvId getIdvId() {
        return request.getIdvId();
    }

    public Aliases getAliases() {
        return request.getAliases();
    }

    public Collection<String> getAliasTypes() {
        return request.getAliasTypes();
    }

    public boolean isProtectSensitiveData() {
        return request.isProtectSensitiveData();
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

    public <T> Stream<T> query(Function<MethodSequence, Optional<T>> query)  {
        return sequences.stream().map(query).flatMap(Optional::stream);
    }

    public Context withOnlyEligibleAndIncompleteSequences() {
        return toBuilder()
                .sequences(sequences.withEligibleAndIncompleteOnly())
                .build();
    }

    public Methods getNextMethods(String methodName) {
        return sequences.getNextMethods(methodName);
    }

    public boolean query(Predicate<MethodSequence> predicate) {
        return sequences.stream().anyMatch(predicate);
    }

    public Context updateMethods(UnaryOperator<Method> function) {
        return withSequences(sequences.updateMethods(function));
    }

    public boolean hasMoreCompletedSequencesThan(Context original) {
        return getCompletedSequenceCount() > original.getCompletedSequenceCount();
    }

    public boolean hasMoreCompletedMethodsThan(Context original) {
        return getCompletedMethodCount() > original.getCompletedMethodCount();
    }

    private long getCompletedSequenceCount() {
        return sequences.getCompletedCount();
    }

    private long getCompletedMethodCount() {
        return sequences.getCompletedMethodCount();
    }

}
