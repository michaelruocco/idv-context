package uk.co.idv.context.entities.lockout;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.lockout.attempt.Attempt;
import uk.co.idv.context.entities.lockout.attempt.AttemptMother;
import uk.co.idv.context.entities.lockout.policy.RecordAttemptRequest;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultRecordAttemptRequestTest {

    @Test
    void shouldSetSequenceComplete() {
        RecordAttemptRequest request = DefaultRecordAttemptRequest.builder()
                .sequenceComplete(true)
                .build();

        assertThat(request.isSequenceComplete()).isTrue();
    }

    @Test
    void shouldSetMethodComplete() {
        RecordAttemptRequest request = DefaultRecordAttemptRequest.builder()
                .methodComplete(true)
                .build();

        assertThat(request.isMethodComplete()).isTrue();
    }

    @Test
    void shouldSetAttempt() {
        Attempt attempt = AttemptMother.build();

        RecordAttemptRequest request = DefaultRecordAttemptRequest.builder()
                .attempt(attempt)
                .build();

        assertThat(request.getAttempt()).isEqualTo(attempt);
    }

    @Test
    void shouldReturnIdvIdFromAttempt() {
        Attempt attempt = AttemptMother.build();

        RecordAttemptRequest request = DefaultRecordAttemptRequest.builder()
                .attempt(attempt)
                .build();

        assertThat(request.getIdvId()).isEqualTo(attempt.getIdvId());
    }

    @Test
    void shouldReturnTimestampFromAttempt() {
        Attempt attempt = AttemptMother.build();

        RecordAttemptRequest request = DefaultRecordAttemptRequest.builder()
                .attempt(attempt)
                .build();

        assertThat(request.getTimestamp()).isEqualTo(attempt.getTimestamp());
    }

    @Test
    void shouldReturnAliasesFromAttempt() {
        Attempt attempt = AttemptMother.build();

        RecordAttemptRequest request = DefaultRecordAttemptRequest.builder()
                .attempt(attempt)
                .build();

        assertThat(request.getAliases()).isEqualTo(attempt.getAliases());
    }

    @Test
    void shouldReturnChannelIdFromAttempt() {
        Attempt attempt = AttemptMother.build();

        RecordAttemptRequest request = DefaultRecordAttemptRequest.builder()
                .attempt(attempt)
                .build();

        assertThat(request.getChannelId()).isEqualTo(attempt.getChannelId());
    }

    @Test
    void shouldReturnActivityNameFromAttempt() {
        Attempt attempt = AttemptMother.build();

        RecordAttemptRequest request = DefaultRecordAttemptRequest.builder()
                .attempt(attempt)
                .build();

        assertThat(request.getActivityName()).isEqualTo(attempt.getActivityName());
    }

    @Test
    void shouldReturnAliasTypesFromAttempt() {
        Attempt attempt = AttemptMother.build();

        RecordAttemptRequest request = DefaultRecordAttemptRequest.builder()
                .attempt(attempt)
                .build();

        assertThat(request.getAliasTypes()).isEqualTo(attempt.getAliasTypes());
    }

}
