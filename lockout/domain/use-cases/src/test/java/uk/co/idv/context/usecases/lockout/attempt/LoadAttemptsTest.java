package uk.co.idv.context.usecases.lockout.attempt;

import org.junit.jupiter.api.Test;
import uk.co.idv.common.usecases.id.IdGenerator;
import uk.co.idv.context.entities.alias.IdvId;
import uk.co.idv.context.entities.alias.IdvIdMother;
import uk.co.idv.context.entities.lockout.attempt.Attempts;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class LoadAttemptsTest {

    private final IdGenerator idGenerator = mock(IdGenerator.class);
    private final AttemptRepository repository = mock(AttemptRepository.class);

    private final LoadAttempts loader = LoadAttempts.builder()
            .idGenerator(idGenerator)
            .repository(repository)
            .build();

    @Test
    void shouldReturnExistingAttemptsFromRepository() {
        IdvId idvId = IdvIdMother.idvId();
        Attempts expectedAttempts = givenAttemptsExistFor(idvId);

        Attempts attempts = loader.load(idvId);

        assertThat(attempts).isEqualTo(expectedAttempts);
    }

    @Test
    void shouldReturnEmptyAttemptsWithGeneratedIdIfNotAttemptsReturnedFromRepository() {
        IdvId idvId = IdvIdMother.idvId();
        givenNoAttemptsExistFor(idvId);
        UUID expectedId = UUID.randomUUID();
        givenRandomIdGeneratedWithValue(expectedId);

        Attempts attempts = loader.load(idvId);

        assertThat(attempts.getId()).isEqualTo(expectedId);
        assertThat(attempts.getIdvId()).isEqualTo(idvId);
        assertThat(attempts).isEmpty();
    }

    private Attempts givenAttemptsExistFor(IdvId idvId) {
        Attempts attempts = mock(Attempts.class);
        given(repository.load(idvId)).willReturn(Optional.of(attempts));
        return attempts;
    }

    private void givenNoAttemptsExistFor(IdvId idvId) {
        given(repository.load(idvId)).willReturn(Optional.empty());
    }

    private void givenRandomIdGeneratedWithValue(UUID randomId) {
        given(idGenerator.generate()).willReturn(randomId);
    }

}
