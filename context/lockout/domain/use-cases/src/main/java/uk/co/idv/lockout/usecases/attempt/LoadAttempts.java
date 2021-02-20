package uk.co.idv.lockout.usecases.attempt;

import lombok.Builder;
import uk.co.idv.identity.entities.alias.IdvId;
import uk.co.idv.lockout.entities.attempt.Attempts;
import uk.co.mruoc.randomvalue.uuid.UuidGenerator;

@Builder
public class LoadAttempts {

    private final UuidGenerator uuidGenerator;
    private final AttemptRepository repository;

    public Attempts load(IdvId idvId) {
        return repository.load(idvId).orElse(buildEmptyAttempts(idvId));
    }

    private Attempts buildEmptyAttempts(IdvId idvId) {
        return Attempts.builder()
                .id(uuidGenerator.generate())
                .idvId(idvId)
                .build();
    }

}
