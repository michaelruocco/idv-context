package uk.co.idv.context.lockout.attempt;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.alias.Alias;
import uk.co.idv.context.entities.alias.DefaultAliasMother;
import uk.co.idv.context.entities.alias.IdvId;
import uk.co.idv.context.entities.alias.IdvIdMother;
import uk.co.idv.context.entities.policy.PolicyKey;
import uk.co.idv.context.entities.policy.key.ChannelPolicyKeyMother;
import uk.co.idv.context.lockout.attempt.VerificationAttempts.VerificationAttemptsBuilder;

import java.time.Instant;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;

class VerificationAttemptsTest {

    @Test
    void shouldThrowExceptionIfConstructedWithAttemptsWithDifferentIdvIds() {
        IdvId idvId = IdvIdMother.idvId();
        IdvId differentIdvId = IdvIdMother.idvId1();
        Collection<VerificationAttempt> attempts = Arrays.asList(
                VerificationAttemptMother.withIdvId(idvId),
                VerificationAttemptMother.withIdvId(differentIdvId)
        );
        VerificationAttemptsBuilder builder = VerificationAttempts.builder()
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
        Collection<VerificationAttempt> attempts = Collections.singleton(
                VerificationAttemptMother.withIdvId(differentIdvId)
        );
        VerificationAttemptsBuilder builder = VerificationAttempts.builder()
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

        VerificationAttempts attempts = VerificationAttempts.builder()
                .id(id)
                .build();

        assertThat(attempts.getId()).isEqualTo(id);
    }

    @Test
    void shouldReturnIdvId() {
        IdvId idvId = IdvIdMother.idvId();

        VerificationAttempts attempts = VerificationAttempts.builder()
                .idvId(idvId)
                .build();

        assertThat(attempts.getIdvId()).isEqualTo(idvId);
    }

    @Test
    void shouldContainAttempts() {
        IdvId idvId = IdvIdMother.idvId();
        Collection<VerificationAttempt> attemptCollection = Arrays.asList(
                VerificationAttemptMother.withIdvId(idvId),
                VerificationAttemptMother.withIdvId(idvId)
        );

        VerificationAttempts attempts = VerificationAttempts.builder()
                .idvId(idvId)
                .attempts(attemptCollection)
                .build();

        assertThat(attempts).containsExactlyElementsOf(attemptCollection);
    }

    @Test
    void shouldThrowExceptionIfAttemptAddedWithDifferentIdvId() {
        IdvId idvId = IdvIdMother.idvId();
        VerificationAttempts attempts = VerificationAttemptsMother.withIdvId(idvId);
        IdvId otherIdvId = IdvIdMother.idvId1();
        VerificationAttempt attempt = VerificationAttemptMother.withIdvId(otherIdvId);

        IdvIdMismatchException error = catchThrowableOfType(
                () -> attempts.add(attempt),
                IdvIdMismatchException.class
        );

        assertThat(error.getIdvIds()).containsExactly(otherIdvId, idvId);
    }

    @Test
    void shouldAddAttemptWithSameIdvId() {
        IdvId idvId = IdvIdMother.idvId();
        VerificationAttempts attempts = VerificationAttemptsMother.withIdvId(idvId);
        VerificationAttempt attempt = VerificationAttemptMother.withIdvId(idvId);

        VerificationAttempts updatedAttempts = attempts.add(attempt);

        assertThat(updatedAttempts).containsExactly(attempt);
    }

    @Test
    void shouldRemoveAttempt() {
        VerificationAttempt attemptToRemove = VerificationAttemptMother.build();
        VerificationAttempts attempts = VerificationAttemptsMother.builder()
                .idvId(attemptToRemove.getIdvId())
                .attempts(Collections.singleton(attemptToRemove))
                .build();
        VerificationAttempts attemptsToRemove = VerificationAttemptsMother.withAttempts(attemptToRemove);

        VerificationAttempts updatedAttempts = attempts.remove(attemptsToRemove);

        assertThat(updatedAttempts).doesNotContain(attemptToRemove);
    }

    @Test
    void shouldReturnMostRecentTimestamp() {
        Instant expectedMostRecent = Instant.parse("2020-07-25T09:15:00.000Z");
        Collection<VerificationAttempt> attemptCollection = Arrays.asList(
                VerificationAttemptMother.withTimestamp(Instant.parse("2020-07-25T09:05:00.000Z")),
                VerificationAttemptMother.withTimestamp(expectedMostRecent)
        );
        VerificationAttempts attempts = VerificationAttempts.builder()
                .idvId(IdvIdMother.idvId())
                .attempts(attemptCollection)
                .build();

        Instant mostRecent = attempts.getMostRecentTimestamp();

        assertThat(mostRecent).isEqualTo(expectedMostRecent);
    }

    @Test
    void shouldReturnAttemptsWithAlias() {
        Alias alias = DefaultAliasMother.build();
        VerificationAttempt attemptWithAlias = VerificationAttemptMother.withAlias(alias);
        Collection<VerificationAttempt> attemptCollection = Arrays.asList(
                VerificationAttemptMother.withAlias(IdvIdMother.idvId()),
                attemptWithAlias
        );
        VerificationAttempts attempts = VerificationAttempts.builder()
                .idvId(IdvIdMother.idvId())
                .attempts(attemptCollection)
                .build();

        VerificationAttempts attemptsWithAlias = attempts.with(alias);

        assertThat(attemptsWithAlias).containsExactly(attemptWithAlias);
    }

    @Test
    void shouldReturnAttemptsApplyingToPolicyKey() {
        PolicyKey key = ChannelPolicyKeyMother.defaultChannelKey();
        VerificationAttempt applicableAttempt = VerificationAttemptMother.withChannelId(key.getChannelId());
        Collection<VerificationAttempt> attemptCollection = Arrays.asList(
                VerificationAttemptMother.withChannelId("other-channel"),
                applicableAttempt
        );
        VerificationAttempts attempts = VerificationAttempts.builder()
                .idvId(IdvIdMother.idvId())
                .attempts(attemptCollection)
                .build();

        VerificationAttempts applicableAttempts = attempts.applyingTo(key);

        assertThat(applicableAttempts).containsExactly(applicableAttempt);
    }

}
