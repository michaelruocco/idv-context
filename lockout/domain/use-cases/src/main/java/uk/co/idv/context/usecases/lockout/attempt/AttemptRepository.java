package uk.co.idv.context.usecases.lockout.attempt;

import uk.co.idv.identity.entities.alias.IdvId;
import uk.co.idv.context.entities.lockout.attempt.Attempts;

import java.util.Optional;

public interface AttemptRepository {

    void save(Attempts attempts);

    Optional<Attempts> load(IdvId idvId);

}
