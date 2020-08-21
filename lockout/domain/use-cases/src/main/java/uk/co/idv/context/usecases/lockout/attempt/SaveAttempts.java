package uk.co.idv.context.usecases.lockout.attempt;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.context.entities.lockout.attempt.Attempt;
import uk.co.idv.context.entities.lockout.attempt.Attempts;

@Builder
@Slf4j
public class SaveAttempts {

    private final LoadAttempts loadAttempts;
    private final AttemptRepository repository;

    public Attempts save(Attempt attempt) {
        Attempts existing = loadAttempts.load(attempt.getIdvId());
        return save(attempt, existing);
    }

    public Attempts save(Attempt attempt, Attempts existing) {
        Attempts updated = existing.add(attempt);
        repository.save(updated);
        return updated;
    }

    public Attempts save(Attempts attempts) {
        repository.save(attempts);
        return attempts;
    }

}
