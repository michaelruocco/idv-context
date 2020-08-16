package uk.co.idv.context.usecases.lockout.attempt;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.context.entities.lockout.attempt.Attempt;
import uk.co.idv.context.entities.lockout.attempt.Attempts;

@Builder
@Slf4j
public class SaveAttempt {

    private final LoadAttempts loadAttempts;
    private final AttemptsRepository repository;

    public void save(Attempt attempt) {
        Attempts attempts = loadAttempts.load(attempt.getIdvId());
        Attempts updated = attempts.add(attempt);
        repository.save(updated);
    }

}
