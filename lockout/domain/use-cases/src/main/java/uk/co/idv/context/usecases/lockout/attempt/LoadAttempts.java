package uk.co.idv.context.usecases.lockout.attempt;

import lombok.Builder;
import uk.co.idv.common.usecases.id.IdGenerator;
import uk.co.idv.identity.entities.alias.IdvId;
import uk.co.idv.context.entities.lockout.attempt.Attempts;

@Builder
public class LoadAttempts {

    private final IdGenerator idGenerator;
    private final AttemptRepository repository;

    public Attempts load(IdvId idvId) {
        return repository.load(idvId).orElse(buildEmptyAttempts(idvId));
    }

    private Attempts buildEmptyAttempts(IdvId idvId) {
        return Attempts.builder()
                .id(idGenerator.generate())
                .idvId(idvId)
                .build();
    }

}
