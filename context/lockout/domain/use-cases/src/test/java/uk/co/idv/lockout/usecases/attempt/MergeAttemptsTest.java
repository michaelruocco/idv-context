package uk.co.idv.lockout.usecases.attempt;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import uk.co.idv.identity.entities.alias.Aliases;
import uk.co.idv.identity.entities.alias.AliasesMother;
import uk.co.idv.identity.entities.alias.IdvId;
import uk.co.idv.identity.entities.alias.IdvIdMother;
import uk.co.idv.identity.entities.event.MergeIdentitiesEvent;
import uk.co.idv.lockout.entities.attempt.Attempt;
import uk.co.idv.lockout.entities.attempt.AttemptMother;
import uk.co.idv.lockout.entities.attempt.Attempts;
import uk.co.idv.lockout.entities.attempt.AttemptsMother;
import uk.co.mruoc.randomvalue.uuid.UuidGenerator;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class MergeAttemptsTest {

    private final IdvId originalIdvId1 = IdvIdMother.idvId();
    private final IdvId originalIdvId2 = IdvIdMother.idvId1();
    private final IdvId originalIdvId3 = IdvIdMother.withValue("428c3e06-853b-415a-b940-7b4330d8a668");
    private final Aliases originalIdvIds = AliasesMother.with(originalIdvId1, originalIdvId2, originalIdvId3);
    private final Attempt originalIdvId1Attempt = AttemptMother.withIdvId(originalIdvId1);
    private final Attempt originalIdvId2Attempt = AttemptMother.withIdvId(originalIdvId2);
    private final IdvId mergedIdvId = IdvIdMother.withValue("f162983f-7208-4839-af10-bc0c3b1955fa");

    private final UuidGenerator uuidGenerator = mock(UuidGenerator.class);
    private final AttemptRepository repository = mock(AttemptRepository.class);

    private final MergeAttempts mergeAttempts = MergeAttempts.builder()
            .uuidGenerator(uuidGenerator)
            .repository(repository)
            .build();

    @Test
    void shouldDeleteAttemptsForOriginalIdentities() {
        MergeIdentitiesEvent event = givenMergeIdentitiesEvent();
        givenAttemptsLoadedForOriginalIdvIds();

        mergeAttempts.merged(event);

        ArgumentCaptor<Collection<IdvId>> captor = ArgumentCaptor.forClass(Collection.class);
        verify(repository).delete(captor.capture());
        assertThat(captor.getValue()).containsExactly(originalIdvId1, originalIdvId2);
    }

    @Test
    void shouldUpdateAllAttemptsWithMergedIdvIdBeforeUpdating() {
        MergeIdentitiesEvent event = givenMergeIdentitiesEvent();
        givenAttemptsLoadedForOriginalIdvIds();

        mergeAttempts.merged(event);

        ArgumentCaptor<Attempts> captor = ArgumentCaptor.forClass(Attempts.class);
        verify(repository).save(captor.capture());
        Attempts savedAttempts = captor.getValue();
        assertThat(savedAttempts.getIdvId()).isEqualTo(mergedIdvId);
        assertThat(savedAttempts).containsExactly(
                originalIdvId1Attempt.withIdvId(mergedIdvId),
                originalIdvId2Attempt.withIdvId(mergedIdvId)
        );
    }

    private MergeIdentitiesEvent givenMergeIdentitiesEvent() {
        MergeIdentitiesEvent event = mock(MergeIdentitiesEvent.class);
        given(event.getOriginalIdvIds()).willReturn(originalIdvIds);
        given(event.getMergedIdvId()).willReturn(mergedIdvId);
        return event;
    }

    private void givenAttemptsLoadedForOriginalIdvIds() {
        givenAttemptsLoadedForIdvId(originalIdvId1, originalIdvId1Attempt);
        givenAttemptsLoadedForIdvId(originalIdvId2, originalIdvId2Attempt);
        given(repository.load(originalIdvId3)).willReturn(Optional.empty());
    }

    private void givenAttemptsLoadedForIdvId(IdvId idvId, Attempt... attempts) {
        given(repository.load(idvId)).willReturn(Optional.of(toAttempts(idvId, attempts)));
    }

    private static Attempts toAttempts(IdvId idvId, Attempt... attempts) {
        return AttemptsMother.builder()
                .idvId(idvId)
                .attempts(Arrays.asList(attempts))
                .build();
    }

}
