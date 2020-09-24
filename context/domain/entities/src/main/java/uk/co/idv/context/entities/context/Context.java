package uk.co.idv.context.entities.context;

import lombok.Builder;
import lombok.Data;
import uk.co.idv.context.entities.activity.Activity;
import uk.co.idv.context.entities.context.create.DefaultCreateContextRequest;
import uk.co.idv.context.entities.context.method.otp.Otp;
import uk.co.idv.context.entities.context.sequence.Sequences;
import uk.co.idv.identity.entities.channel.Channel;
import uk.co.idv.identity.entities.identity.Identity;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Builder
@Data
public class Context {

    private final UUID id;
    private final Instant created;
    private final DefaultCreateContextRequest request;
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

    public Optional<Otp> findNextIncompleteEligibleOtp() {
        return sequences.findNextIncompleteEligibleOtp();
    }

    public boolean isEligible() {
        return sequences.isEligible();
    }

    public boolean isComplete() {
        return sequences.isComplete();
    }

    public boolean isSuccessful() { return sequences.isSuccessful(); }

}
