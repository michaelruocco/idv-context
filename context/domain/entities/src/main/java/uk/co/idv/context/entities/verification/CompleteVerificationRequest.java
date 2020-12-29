package uk.co.idv.context.entities.verification;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Builder
@Data
public class CompleteVerificationRequest {

    private final UUID verificationId;
    private final boolean successful;
    private final Instant timestamp;

}
