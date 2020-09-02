package uk.co.idv.lockout.usecases.attempt;

import uk.co.idv.identity.entities.alias.IdvId;
import uk.co.idv.lockout.entities.attempt.Attempts;

import java.util.Optional;

public interface AttemptRepository {

    void save(Attempts attempts);

    Optional<Attempts> load(IdvId idvId);

}
