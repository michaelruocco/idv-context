package uk.co.idv.context.usecases.lockout.attempt;

import uk.co.idv.context.entities.alias.IdvId;
import uk.co.idv.context.entities.lockout.attempt.Attempts;

import java.util.Optional;

public interface AttemptsRepository {

    void save(Attempts attempts);

    Optional<Attempts> load(IdvId idvId);

}
