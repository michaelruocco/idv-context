package uk.co.idv.lockout.adapter.repository;

import org.junit.jupiter.api.Test;
import uk.co.idv.identity.entities.alias.IdvId;
import uk.co.idv.identity.entities.alias.IdvIdMother;
import uk.co.idv.lockout.entities.attempt.Attempts;
import uk.co.idv.lockout.entities.attempt.AttemptsMother;
import uk.co.idv.lockout.usecases.attempt.AttemptRepository;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class InMemoryAttemptRepositoryTest {

    private final AttemptRepository repository = new InMemoryAttemptRepository();

    @Test
    void shouldReturnEmptyOptionalIfNoAttemptsFoundByIdvId() {
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

    @Test
    void shouldDeleteAttemptsByIdvId() {
        IdvId idvId1 = IdvIdMother.idvId();
        Attempts attempts1 = AttemptsMother.withIdvId(idvId1);
        IdvId idvId2 = IdvIdMother.idvId1();
        Attempts attempts2 = AttemptsMother.withIdvId(idvId2);
        repository.save(attempts1);
        repository.save(attempts2);

        repository.delete(Arrays.asList(idvId1, idvId2));

        assertThat(repository.load(idvId1)).isEmpty();
        assertThat(repository.load(idvId2)).isEmpty();
    }

}
