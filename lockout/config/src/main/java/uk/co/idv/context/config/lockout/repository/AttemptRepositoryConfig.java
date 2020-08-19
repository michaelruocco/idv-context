package uk.co.idv.context.config.lockout.repository;

import uk.co.idv.context.usecases.lockout.attempt.AttemptRepository;

public interface AttemptRepositoryConfig {

    AttemptRepository attemptRepository();

}
