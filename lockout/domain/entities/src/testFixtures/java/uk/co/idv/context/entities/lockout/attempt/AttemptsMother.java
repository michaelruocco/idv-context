package uk.co.idv.context.entities.lockout.attempt;

import uk.co.idv.context.entities.alias.IdvId;
import uk.co.idv.context.entities.alias.IdvIdMother;
import uk.co.idv.context.entities.lockout.attempt.Attempts.AttemptsBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;

public interface AttemptsMother {

    static Attempts build() {
        return builder().build();
    }

    static Attempts withIdvId(IdvId idvId) {
        return builder().idvId(idvId).build();
    }

    static Attempts withNumberOfAttempts(int numberOfAttempts) {
        Collection<Attempt> attempts = new ArrayList<>();
        for (int i = 0; i < numberOfAttempts; i++) {
            attempts.add(AttemptMother.build());
        }
        return builder().attempts(attempts).build();
    }

    static Attempts empty() {
        return withNumberOfAttempts(0);
    }

    static Attempts withAttempts(Attempt... attempts) {
        return builder().attempts(Arrays.asList(attempts)).build();
    }

    static AttemptsBuilder builder() {
        return Attempts.builder()
                .id(UUID.fromString("c4df70d4-3248-43b8-ac04-2bfddff4c506"))
                .idvId(IdvIdMother.idvId());
    }

}
