package uk.co.idv.lockout.usecases.attempt;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.common.usecases.id.IdGenerator;
import uk.co.idv.identity.entities.alias.IdvId;
import uk.co.idv.identity.entities.event.MergeIdentitiesEvent;
import uk.co.idv.identity.usecases.identity.merge.MergeIdentitiesHandler;
import uk.co.idv.lockout.entities.attempt.Attempt;
import uk.co.idv.lockout.entities.attempt.Attempts;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Builder
@Slf4j
public class MergeAttempts implements MergeIdentitiesHandler {

    private final IdGenerator idGenerator;
    private final AttemptRepository repository;

    @Override
    public void merged(MergeIdentitiesEvent event) {
        Collection<Attempts> originalAttempts = loadOriginalIdentityAttempts(event);
        repository.delete(toIdvIds(originalAttempts));
        repository.save(toAttemptsToMerge(event.getMergedIdvId(), originalAttempts));
    }

    private Collection<Attempts> loadOriginalIdentityAttempts(MergeIdentitiesEvent event) {
        return event.getOriginalIdvIds().stream()
                .map(alias -> (IdvId) alias)
                .map(repository::load)
                .flatMap(Optional::stream)
                .collect(Collectors.toList());
    }

    private Collection<IdvId> toIdvIds(Collection<Attempts> originalAttempts) {
        return originalAttempts.stream()
                .map(Attempts::getIdvId)
                .collect(Collectors.toList());
    }

    private Attempts toAttemptsToMerge(IdvId mergedIdvId, Collection<Attempts> originalAttempts) {
        return Attempts.builder()
                .attempts(updateIdvIds(mergedIdvId, originalAttempts))
                .idvId(mergedIdvId)
                .id(idGenerator.generate())
                .build();
    }

    private Collection<Attempt> updateIdvIds(IdvId mergedIdvId, Collection<Attempts> originalAttempts) {
        return originalAttempts.stream()
                .flatMap(Attempts::stream)
                .map(attempt -> attempt.withIdvId(mergedIdvId))
                .collect(Collectors.toList());
    }

}
