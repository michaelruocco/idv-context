package uk.co.idv.lockout.usecases.attempt;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import uk.co.idv.identity.entities.alias.IdvId;
import uk.co.idv.identity.entities.alias.IdvIdMother;
import uk.co.idv.lockout.entities.attempt.Attempt;
import uk.co.idv.lockout.entities.attempt.AttemptMother;
import uk.co.idv.lockout.entities.attempt.Attempts;
import uk.co.idv.lockout.entities.attempt.AttemptsMother;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class SaveAttemptsTest {

    private final LoadAttempts loadAttempts = mock(LoadAttempts.class);
    private final AttemptRepository repository = mock(AttemptRepository.class);

    private final SaveAttempts saveAttempts = SaveAttempts.builder()
            .loadAttempts(loadAttempts)
            .repository(repository)
            .build();

    @Test
    void shouldAddAttemptToLoadedAttemptsAndSave() {
        IdvId idvId = IdvIdMother.idvId();
        Attempts loadedAttempts = AttemptsMother.withIdvId(idvId);
        given(loadAttempts.load(idvId)).willReturn(loadedAttempts);
        Attempt attempt = AttemptMother.withIdvId(idvId);

        saveAttempts.save(attempt);

        ArgumentCaptor<Attempts> captor = ArgumentCaptor.forClass(Attempts.class);
        verify(repository).save(captor.capture());
        Attempts savedAttempts = captor.getValue();
        assertThat(savedAttempts)
                .hasSize(loadedAttempts.size() + 1)
                .containsAll(loadedAttempts)
                .contains(attempt);
    }

    @Test
    void shouldSaveAttempts() {
        Attempts attempts = AttemptsMother.build();

        Attempts savedAttempts = saveAttempts.save(attempts);

        assertThat(savedAttempts).isEqualTo(attempts);
        verify(repository).save(attempts);
    }

}
