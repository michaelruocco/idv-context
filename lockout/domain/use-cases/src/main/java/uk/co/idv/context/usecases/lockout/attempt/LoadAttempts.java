package uk.co.idv.context.usecases.lockout.attempt;

import lombok.Builder;
import uk.co.idv.context.entities.alias.IdvId;
import uk.co.idv.context.entities.lockout.attempt.Attempts;
import uk.co.idv.context.usecases.common.IdGenerator;

@Builder
public class LoadAttempts {

    private final IdGenerator idGenerator;
    private final AttemptsRepository repository;

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
