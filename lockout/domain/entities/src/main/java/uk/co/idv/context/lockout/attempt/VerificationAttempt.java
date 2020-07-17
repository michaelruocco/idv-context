package uk.co.idv.context.lockout.attempt;

import lombok.Builder;
import lombok.Data;
import uk.co.idv.context.entities.alias.Alias;
import uk.co.idv.context.entities.alias.IdvId;

import java.time.Instant;
import java.util.UUID;

@Builder
@Data
public class VerificationAttempt {

    private final String channelId;
    private final String activityName;
    private final Alias alias;
    private final IdvId idvId;

    private final UUID contextId;
    private final String methodName;
    private final UUID verificationId;
    private final Instant timestamp;
    private final boolean successful;

}
