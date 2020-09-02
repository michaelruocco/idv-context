package uk.co.idv.lockout.config.repository;

import uk.co.idv.lockout.usecases.attempt.AttemptRepository;

public interface AttemptRepositoryConfig {

    AttemptRepository attemptRepository();

}
