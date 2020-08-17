package uk.co.idv.context.config.lockout.repository;

import uk.co.idv.context.usecases.lockout.attempt.AttemptsRepository;

public interface AttemptsRepositoryConfig {

    AttemptsRepository attemptsRepository();

}
