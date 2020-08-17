package uk.co.idv.context.entities.lockout.attempt;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.alias.Alias;
import uk.co.idv.context.entities.alias.DefaultAliasMother;
import uk.co.idv.context.entities.alias.IdvId;
import uk.co.idv.context.entities.alias.IdvIdMother;
import uk.co.idv.context.entities.policy.PolicyKey;
import uk.co.idv.context.entities.policy.key.ChannelPolicyKeyMother;
import uk.co.idv.context.entities.lockout.attempt.Attempts.AttemptsBuilder;

import java.time.Instant;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;

class AttemptsTest {

    @Test
    void shouldThrowExceptionIfConstructedWithAttemptsWithDifferentIdvIds() {
        IdvId idvId = IdvIdMother.idvId();
        IdvId differentIdvId = IdvIdMother.idvId1();
        Collection<Attempt> attempts = Arrays.asList(
                AttemptMother.withIdvId(idvId),
                AttemptMother.withIdvId(differentIdvId)
        );
        AttemptsBuilder builder = Attempts.builder()
                .attempts(attempts);

        IdvIdMismatchException error = catchThrowableOfType(
                builder::build,
                IdvIdMismatchException.class
        );

        assertThat(error.getIdvIds()).containsExactly(idvId, differentIdvId);
    }

    @Test
    void shouldThrowExceptionIfConstructedWithAttemptWithDifferentIdvId() {
        IdvId idvId = IdvIdMother.idvId();
        IdvId differentIdvId = IdvIdMother.idvId1();
        Collection<Attempt> attempts = Collections.singleton(
                AttemptMother.withIdvId(differentIdvId)
        );
        AttemptsBuilder builder = Attempts.builder()
                .idvId(idvId)
                .attempts(attempts);

        IdvIdMismatchException error = catchThrowableOfType(
                builder::build,
                IdvIdMismatchException.class
        );

        assertThat(error.getIdvIds()).containsExactly(idvId, differentIdvId);
    }

    @Test
    void shouldReturnId() {
        UUID id = UUID.randomUUID();

        Attempts attempts = Attempts.builder()
                .id(id)
                .build();

        assertThat(attempts.getId()).isEqualTo(id);
    }

    @Test
    void shouldReturnIdvId() {
        IdvId idvId = IdvIdMother.idvId();

        Attempts attempts = Attempts.builder()
                .idvId(idvId)
                .build();

        assertThat(attempts.getIdvId()).isEqualTo(idvId);
    }

    @Test
    void shouldContainAttempts() {
        IdvId idvId = IdvIdMother.idvId();
        Collection<Attempt> attemptCollection = Arrays.asList(
                AttemptMother.withIdvId(idvId),
                AttemptMother.withIdvId(idvId)
        );

        Attempts attempts = Attempts.builder()
                .idvId(idvId)
                .attempts(attemptCollection)
                .build();

        assertThat(attempts).containsExactlyElementsOf(attemptCollection);
    }

    @Test
    void shouldReturnSize() {
        IdvId idvId = IdvIdMother.idvId();
        Collection<Attempt> attemptCollection = Arrays.asList(
                AttemptMother.withIdvId(idvId),
                AttemptMother.withIdvId(idvId)
        );

        Attempts attempts = Attempts.builder()
                .idvId(idvId)
                .attempts(attemptCollection)
                .build();

        assertThat(attempts.size()).isEqualTo(2);
    }

    @Test
    void shouldThrowExceptionIfAttemptAddedWithDifferentIdvId() {
        IdvId idvId = IdvIdMother.idvId();
        Attempts attempts = AttemptsMother.withIdvId(idvId);
        IdvId otherIdvId = IdvIdMother.idvId1();
        Attempt attempt = AttemptMother.withIdvId(otherIdvId);

        IdvIdMismatchException error = catchThrowableOfType(
                () -> attempts.add(attempt),
                IdvIdMismatchException.class
        );

        assertThat(error.getIdvIds()).containsExactly(otherIdvId, idvId);
    }

    @Test
    void shouldAddAttemptWithSameIdvId() {
        IdvId idvId = IdvIdMother.idvId();
        Attempts attempts = AttemptsMother.withIdvId(idvId);
        Attempt attempt = AttemptMother.withIdvId(idvId);

        Attempts updatedAttempts = attempts.add(attempt);

        assertThat(updatedAttempts).containsExactly(attempt);
    }

    @Test
    void shouldRemoveAttempt() {
        Attempt attemptToRemove = AttemptMother.build();
        Attempts attempts = AttemptsMother.builder()
                .idvId(attemptToRemove.getIdvId())
                .attempts(Collections.singleton(attemptToRemove))
                .build();
        Attempts attemptsToRemove = AttemptsMother.withAttempts(attemptToRemove);

        Attempts updatedAttempts = attempts.remove(attemptsToRemove);

        assertThat(updatedAttempts).isEmpty();
    }

    @Test
    void shouldReturnMostRecentTimestamp() {
        Instant expectedMostRecent = Instant.parse("2020-07-25T09:15:00.000Z");
        Collection<Attempt> attemptCollection = Arrays.asList(
                AttemptMother.withTimestamp(Instant.parse("2020-07-25T09:05:00.000Z")),
                AttemptMother.withTimestamp(expectedMostRecent)
        );
        Attempts attempts = Attempts.builder()
                .idvId(IdvIdMother.idvId())
                .attempts(attemptCollection)
                .build();

        Instant mostRecent = attempts.getMostRecentTimestamp();

        assertThat(mostRecent).isEqualTo(expectedMostRecent);
    }

    @Test
    void shouldReturnAttemptsWithAlias() {
        Alias alias = DefaultAliasMother.build();
        Attempt attemptWithAlias = AttemptMother.withAlias(alias);
        Collection<Attempt> attemptCollection = Arrays.asList(
                AttemptMother.withAlias(IdvIdMother.idvId()),
                attemptWithAlias
        );
        Attempts attempts = Attempts.builder()
                .idvId(IdvIdMother.idvId())
                .attempts(attemptCollection)
                .build();

        Attempts attemptsWithAlias = attempts.with(alias);

        assertThat(attemptsWithAlias).containsExactly(attemptWithAlias);
    }

    @Test
    void shouldReturnAttemptsApplyingToPolicyKey() {
        PolicyKey key = ChannelPolicyKeyMother.build();
        Attempt applicableAttempt = AttemptMother.withChannelId(key.getChannelId());
        Collection<Attempt> attemptCollection = Arrays.asList(
                AttemptMother.withChannelId("other-channel"),
                applicableAttempt
        );
        Attempts attempts = Attempts.builder()
                .idvId(IdvIdMother.idvId())
                .attempts(attemptCollection)
                .build();

        Attempts applicableAttempts = attempts.applyingTo(key);

        assertThat(applicableAttempts).containsExactly(applicableAttempt);
    }

    @Test
    void shouldReturnAttemptsWithUpdatedValuesAndAllOtherValuesUnchanged() {
        Attempts attempts = AttemptsMother.withAttempts(AttemptMother.build());

        Attempts updatedAttempts = attempts.withValues(Collections.emptyList());

        assertThat(updatedAttempts).isEmpty();
        assertThat(updatedAttempts.getId()).isEqualTo(attempts.getId());
        assertThat(updatedAttempts.getIdvId()).isEqualTo(attempts.getIdvId());
    }

}
