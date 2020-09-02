package uk.co.idv.context.adapter.repository;

import org.junit.jupiter.api.Test;
import uk.co.idv.identity.entities.alias.IdvId;
import uk.co.idv.identity.entities.alias.IdvIdMother;
import uk.co.idv.context.entities.lockout.attempt.Attempts;
import uk.co.idv.context.entities.lockout.attempt.AttemptsMother;
import uk.co.idv.context.usecases.lockout.attempt.AttemptRepository;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class InMemoryAttemptRepositoryTest {

    private final AttemptRepository repository = new InMemoryAttemptRepository();

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
