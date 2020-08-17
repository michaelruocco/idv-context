package uk.co.idv.context.adapter.repository;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.alias.IdvId;
import uk.co.idv.context.entities.alias.IdvIdMother;
import uk.co.idv.context.entities.lockout.attempt.Attempts;
import uk.co.idv.context.entities.lockout.attempt.AttemptsMother;
import uk.co.idv.context.usecases.lockout.attempt.AttemptsRepository;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class InMemoryAttemptsRepositoryTest {

    private final AttemptsRepository repository = new InMemoryAttemptsRepository();

    @Test
    void shouldEmptyOptionalIfNoAttemptsFoundByIdvId() {
        IdvId idvId = IdvIdMother.withValue(UUID.randomUUID());

        Optional<Attempts> loaded = repository.load(idvId);

        assertThat(loaded).isEmpty();
    }

    @Test
    void shouldReturnSavedAttemptsByIdvId() {
        Attempts expected = AttemptsMother.build();
        repository.save(expected);

        Optional<Attempts> loaded = repository.load(expected.getIdvId());

        assertThat(loaded).contains(expected);
    }

}
