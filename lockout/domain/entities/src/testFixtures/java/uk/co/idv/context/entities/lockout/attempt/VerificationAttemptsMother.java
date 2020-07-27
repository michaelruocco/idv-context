package uk.co.idv.context.entities.lockout.attempt;

import uk.co.idv.context.entities.alias.IdvId;
import uk.co.idv.context.entities.alias.IdvIdMother;
import uk.co.idv.context.entities.lockout.attempt.VerificationAttempts.VerificationAttemptsBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
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

    public static VerificationAttempts withNumberOfAttempts(int numberOfAttempts) {
        Collection<VerificationAttempt> attempts = new ArrayList<>();
        for (int i = 0; i < numberOfAttempts; i++) {
            attempts.add(VerificationAttemptMother.build());
        }
        return builder().attempts(attempts).build();
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
