package uk.co.idv.context.usecases.lockout.attempt;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import uk.co.idv.context.entities.alias.IdvId;
import uk.co.idv.context.entities.alias.IdvIdMother;
import uk.co.idv.context.entities.lockout.attempt.Attempt;
import uk.co.idv.context.entities.lockout.attempt.AttemptMother;
import uk.co.idv.context.entities.lockout.attempt.Attempts;
import uk.co.idv.context.entities.lockout.attempt.AttemptsMother;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class SaveAttemptTest {

    private final LoadAttempts loadAttempts = mock(LoadAttempts.class);
    private final AttemptsRepository repository = mock(AttemptsRepository.class);

    private final SaveAttempt saveAttempt = SaveAttempt.builder()
            .loadAttempts(loadAttempts)
            .repository(repository)
            .build();

    @Test
    void shouldAddAttemptToLoadedAttemptsAndSave() {
        IdvId idvId = IdvIdMother.idvId();
        Attempts loadedAttempts = AttemptsMother.withIdvId(idvId);
        given(loadAttempts.load(idvId)).willReturn(loadedAttempts);
        Attempt attempt = AttemptMother.withIdvId(idvId);

        saveAttempt.save(attempt);

        ArgumentCaptor<Attempts> captor = ArgumentCaptor.forClass(Attempts.class);
        verify(repository).save(captor.capture());
        Attempts savedAttempts = captor.getValue();
        assertThat(savedAttempts)
                .hasSize(loadedAttempts.size() + 1)
                .containsAll(loadedAttempts)
                .contains(attempt);
    }
}
