package uk.co.idv.lockout.usecases.attempt;

import org.junit.jupiter.api.Test;
import uk.co.idv.identity.entities.alias.IdvId;
import uk.co.idv.identity.entities.alias.IdvIdMother;
import uk.co.idv.lockout.entities.attempt.Attempts;
import uk.co.mruoc.randomvalue.uuid.UuidGenerator;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class LoadAttemptsTest {

    private final UuidGenerator uuidGenerator = mock(UuidGenerator.class);
    private final AttemptRepository repository = mock(AttemptRepository.class);

    private final LoadAttempts loader = LoadAttempts.builder()
            .uuidGenerator(uuidGenerator)
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
        given(uuidGenerator.generate()).willReturn(randomId);
    }

}
