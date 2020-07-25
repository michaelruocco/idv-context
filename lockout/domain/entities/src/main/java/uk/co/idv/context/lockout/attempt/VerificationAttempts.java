package uk.co.idv.context.lockout.attempt;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.With;
import org.apache.commons.collections4.CollectionUtils;
import uk.co.idv.context.entities.alias.Alias;
import uk.co.idv.context.entities.alias.IdvId;
import uk.co.idv.context.entities.policy.PolicyKey;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
public class VerificationAttempts implements Iterable<VerificationAttempt> {

    private final UUID id;
    private final IdvId idvId;

    @With
    @Getter(AccessLevel.NONE)
    private final Collection<VerificationAttempt> attempts;

    public static VerificationAttemptsBuilder builder() {
        return new VerificationAttemptsBuilder();
    }

    @Override
    public Iterator<VerificationAttempt> iterator() {
        return attempts.iterator();
    }

    public IdvId getIdvId() {
        return idvId;
    }

    public int size() {
        return attempts.size();
    }

    public VerificationAttempts add(VerificationAttempt attempt) {
        validate(attempt.getIdvId());
        return withAttempts(addToAttempts(attempt));
    }

    public Instant getMostRecentTimestamp() {
        return getMostRecent().getTimestamp();
    }

    public VerificationAttempts with(Alias alias) {
        return withAttempts(attempts.stream()
                .filter(attempt -> attempt.hasAlias(alias))
                .collect(Collectors.toList()));
    }

    public VerificationAttempts applyingTo(PolicyKey key) {
        return withAttempts(attempts.stream()
                .filter(key::appliesTo)
                .collect(Collectors.toList()));
    }

    private void validate(IdvId attemptIdvId) {
        if (!idvId.equals(attemptIdvId)) {
            throw new IdvIdMismatchException(attemptIdvId, idvId);
        }
    }

    public VerificationAttempts remove(VerificationAttempts attemptsToRemove) {
        return withAttempts(CollectionUtils.removeAll(attempts, attemptsToRemove.attempts));
    }

    private Collection<VerificationAttempt> addToAttempts(VerificationAttempt attempt) {
        Collection<VerificationAttempt> updated = new ArrayList<>(attempts);
        updated.add(attempt);
        return updated;
    }

    private VerificationAttempt getMostRecent() {
        return Collections.max(attempts, Comparator.comparing(VerificationAttempt::getTimestamp));
    }

    public static class VerificationAttemptsBuilder {

        private UUID id;
        private IdvId idvId;
        private Collection<VerificationAttempt> attempts = Collections.emptyList();

        public VerificationAttemptsBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public VerificationAttemptsBuilder idvId(IdvId idvId) {
            this.idvId = idvId;
            return this;
        }

        public VerificationAttemptsBuilder attempts(Collection<VerificationAttempt> attempts) {
            this.attempts = attempts;
            return this;
        }

        public VerificationAttempts build() {
            validate(attempts);
            return new VerificationAttempts(id, idvId, attempts);
        }

        private void validate(Collection<VerificationAttempt> attempts) {
            List<IdvId> idvIds = toDistinctIdvIds(attempts);
            if (idvIds.size() > 1) {
                throw new IdvIdMismatchException(idvIds);
            }
            if (idvIds.size() == 1) {
                validate(idvIds.get(0));
            }
        }

        private static List<IdvId> toDistinctIdvIds(Collection<VerificationAttempt> attempts) {
            return attempts.stream()
                    .map(VerificationAttempt::getIdvId)
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
