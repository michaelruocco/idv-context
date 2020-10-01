package uk.co.idv.context.entities.context;

import lombok.Builder;
import lombok.Data;
import lombok.With;
import uk.co.idv.context.entities.activity.Activity;
import uk.co.idv.context.entities.context.create.ServiceCreateContextRequest;
import uk.co.idv.context.entities.context.method.Method;
import uk.co.idv.context.entities.context.method.otp.delivery.DeliveryMethods;
import uk.co.idv.context.entities.context.method.query.MethodQuery;
import uk.co.idv.context.entities.context.sequence.Sequences;
import uk.co.idv.identity.entities.channel.Channel;
import uk.co.idv.identity.entities.identity.Identity;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;
import java.util.stream.Stream;


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

    public boolean isSuccessful() { return sequences.isSuccessful(); }

    public Duration getDuration() { return sequences.getDuration(); }

    public Context replaceDeliveryMethods(DeliveryMethods newValues) {
        return withSequences(sequences.replaceDeliveryMethods(newValues));
    }

    public <T extends Method> Stream<T> find(MethodQuery<T> query) {
        return sequences.find(query);
    }

    public boolean hasExpired(Instant timestamp) {
        return timestamp.isAfter(expiry);
    }

}
