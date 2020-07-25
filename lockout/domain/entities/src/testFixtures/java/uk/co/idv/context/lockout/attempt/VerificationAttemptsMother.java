package uk.co.idv.context.lockout.attempt;

import uk.co.idv.context.entities.alias.IdvId;
import uk.co.idv.context.entities.alias.IdvIdMother;
import uk.co.idv.context.lockout.attempt.VerificationAttempts.VerificationAttemptsBuilder;

import java.util.Arrays;
import java.util.UUID;

public class VerificationAttemptsMother {

    private VerificationAttemptsMother() {
        // utility class
    }

    public static VerificationAttempts build() {
        return builder().build();
    }

    public static VerificationAttempts withIdvId(IdvId idvId) {
        return builder().idvId(idvId).build();
    }

    public static VerificationAttempts withAttempts(VerificationAttempt... attempts) {
        return builder().attempts(Arrays.asList(attempts)).build();
    }

    public static VerificationAttemptsBuilder builder() {
        return VerificationAttempts.builder()
                .id(UUID.fromString("c4df70d4-3248-43b8-ac04-2bfddff4c506"))
                .idvId(IdvIdMother.idvId());
    }

}
