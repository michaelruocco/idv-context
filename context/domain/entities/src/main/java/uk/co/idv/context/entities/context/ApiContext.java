package uk.co.idv.context.entities.context;

import lombok.Builder;
import lombok.Data;
import uk.co.idv.activity.entities.Activity;
import uk.co.idv.context.entities.context.sequence.Sequences;
import uk.co.idv.identity.entities.alias.Aliases;
import uk.co.idv.identity.entities.channel.Channel;
import uk.co.idv.method.entities.verification.Verifications;

import java.time.Instant;
import java.util.UUID;

@Builder
@Data
public class ApiContext {

    private final UUID id;
    private final Instant created;
    private final Instant expiry;
    private final Channel channel;
    private final Aliases aliases;
    private final Activity activity;
    private final Sequences sequences;

    private final Verifications verifications;

    public boolean isEligible() {
        return sequences.isEligible();
    }

    public boolean isComplete() {
        return sequences.isComplete(verifications);
    }

    public boolean isSuccessful() {
        return sequences.isSuccessful(verifications);
    }

}
