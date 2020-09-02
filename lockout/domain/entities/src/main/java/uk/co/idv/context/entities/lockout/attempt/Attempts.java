package uk.co.idv.context.entities.lockout.attempt;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.With;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.collection.UnmodifiableCollection;
import uk.co.idv.identity.entities.alias.Aliases;
import uk.co.idv.identity.entities.alias.IdvId;
import uk.co.idv.context.entities.policy.PolicyKey;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
public class Attempts implements Iterable<Attempt> {

    private final UUID id;
    private final IdvId idvId;

    @With
    @Getter(AccessLevel.NONE)
    private final Collection<Attempt> values;

    public static AttemptsBuilder builder() {
        return new AttemptsBuilder();
    }

    @Override
    public Iterator<Attempt> iterator() {
        return values.iterator();
    }

    public IdvId getIdvId() {
        return idvId;
    }

    public int size() {
        return values.size();
    }

    public Attempts add(Attempt attempt) {
        validate(attempt.getIdvId());
        return withValues(addToAttempts(attempt));
    }

    public Optional<Instant> getMostRecentTimestamp() {
        return getMostRecent().map(Attempt::getTimestamp);
    }

    public Attempts with(Aliases aliases) {
        return withValues(values.stream()
                .filter(attempt -> attempt.hasAtLeastOneAlias(aliases))
                .collect(Collectors.toList()));
    }

    public Attempts applyingTo(PolicyKey key) {
        return withValues(values.stream()
                .filter(key::appliesTo)
                .collect(Collectors.toList()));
    }

    public Collection<Attempt> toCollection() {
        return UnmodifiableCollection.unmodifiableCollection(values);
    }

    private void validate(IdvId attemptIdvId) {
        if (!idvId.equals(attemptIdvId)) {
            throw new IdvIdMismatchException(attemptIdvId, idvId);
        }
    }

    public Attempts remove(Attempts attemptsToRemove) {
        return withValues(CollectionUtils.removeAll(values, attemptsToRemove.values));
    }

    private Collection<Attempt> addToAttempts(Attempt attempt) {
        Collection<Attempt> updated = new ArrayList<>(values);
        updated.add(attempt);
        return updated;
    }

    private Optional<Attempt> getMostRecent() {
        if (values.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(Collections.max(values, Comparator.comparing(Attempt::getTimestamp)));
    }

    public static class AttemptsBuilder {

        private UUID id;
        private IdvId idvId;
        private Collection<Attempt> attempts = Collections.emptyList();

        public AttemptsBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public AttemptsBuilder idvId(IdvId idvId) {
            this.idvId = idvId;
            return this;
        }

        public AttemptsBuilder attempts(Collection<Attempt> attempts) {
            this.attempts = attempts;
            return this;
        }

        public Attempts build() {
            validate(attempts);
            return new Attempts(id, idvId, attempts);
        }

        private void validate(Collection<Attempt> attempts) {
            List<IdvId> idvIds = toDistinctIdvIds(attempts);
            if (idvIds.size() > 1) {
                throw new IdvIdMismatchException(idvIds);
            }
            if (idvIds.size() == 1) {
                validate(idvIds.get(0));
            }
        }

        private static List<IdvId> toDistinctIdvIds(Collection<Attempt> attempts) {
            return attempts.stream()
                    .map(Attempt::getIdvId)
                    .distinct()
                    .collect(Collectors.toList());
        }

        private void validate(IdvId otherIdvId) {
            if (!idvId.equals(otherIdvId)) {
                throw new IdvIdMismatchException(idvId, otherIdvId);
            }
        }

    }

}
