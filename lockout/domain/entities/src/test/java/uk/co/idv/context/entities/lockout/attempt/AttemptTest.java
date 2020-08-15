package uk.co.idv.context.entities.lockout.attempt;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.alias.Alias;
import uk.co.idv.context.entities.alias.DefaultAliasMother;
import uk.co.idv.context.entities.alias.IdvId;
import uk.co.idv.context.entities.alias.IdvIdMother;
import uk.co.idv.context.entities.lockout.attempt.Attempt.AttemptBuilder;

import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class AttemptTest {

    private final AttemptBuilder builder = Attempt.builder();

    @Test
    void shouldReturnSuccessful() {
        boolean successful = true;

        Attempt attempt = builder.successful(successful).build();

        assertThat(attempt.isSuccessful()).isTrue();
    }

    @Test
    void shouldReturnContextId() {
        UUID contextId = UUID.randomUUID();

        Attempt attempt = builder.contextId(contextId).build();

        assertThat(attempt.getContextId()).isEqualTo(contextId);
    }

    @Test
    void shouldReturnChannelId() {
        String channelId = "channel-id";

        Attempt attempt = builder.channelId(channelId).build();

        assertThat(attempt.getChannelId()).isEqualTo(channelId);
    }

    @Test
    void shouldReturnActivityName() {
        String activityName = "activity-name";

        Attempt attempt = builder.activityName(activityName).build();

        assertThat(attempt.getActivityName()).isEqualTo(activityName);
    }

    @Test
    void shouldReturnAlias() {
        Alias alias = DefaultAliasMother.build();

        Attempt attempt = builder.alias(alias).build();

        assertThat(attempt.getAlias()).isEqualTo(alias);
    }

    @Test
    void shouldReturnAliasType() {
        Alias alias = DefaultAliasMother.build();

        Attempt attempt = builder.alias(alias).build();

        assertThat(attempt.getAliasType()).isEqualTo(alias.getType());
    }

    @Test
    void shouldReturnHasAliasTrueIfAliasMatches() {
        Alias alias = DefaultAliasMother.build();
        Attempt attempt = builder.alias(alias).build();

        boolean hasAlias = attempt.hasAlias(alias);

        assertThat(hasAlias).isTrue();
    }

    @Test
    void shouldReturnHasAliasFalseIfAliasDoesNotMatch() {
        Alias alias = DefaultAliasMother.build();
        Attempt attempt = builder.alias(alias).build();

        boolean hasAlias = attempt.hasAlias(IdvIdMother.idvId());

        assertThat(hasAlias).isFalse();
    }

    @Test
    void shouldReturnIdvId() {
        IdvId idvId = IdvIdMother.idvId();

        Attempt attempt = builder.idvId(idvId).build();

        assertThat(attempt.getIdvId()).isEqualTo(idvId);
    }

    @Test
    void shouldReturnMethodName() {
        String methodName = "method-name";

        Attempt attempt = builder.methodName(methodName).build();

        assertThat(attempt.getMethodName()).isEqualTo(methodName);
    }

    @Test
    void shouldReturnVerificationId() {
        UUID verificationId = UUID.randomUUID();

        Attempt attempt = builder.verificationId(verificationId).build();

        assertThat(attempt.getVerificationId()).isEqualTo(verificationId);
    }

    @Test
    void shouldReturnTimestamp() {
        Instant timestamp = Instant.now();

        Attempt attempt = builder.timestamp(timestamp).build();

        assertThat(attempt.getTimestamp()).isEqualTo(timestamp);
    }

}
