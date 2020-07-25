package uk.co.idv.context.lockout.policy.recordattempt;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.lockout.policy.RecordAttemptPolicy;
import uk.co.idv.context.lockout.policy.RecordAttemptRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class AlwaysRecordAttemptsTest {

    private final RecordAttemptPolicy policy = new AlwaysRecordAttempts();

    @Test
    void shouldReturnType() {
        assertThat(policy.getType()).isEqualTo("always-record");
    }

    @Test
    void shouldNeverRecordAttempt() {
        RecordAttemptRequest anyRequest = mock(RecordAttemptRequest.class);

        boolean shouldRecord = policy.shouldRecordAttempt(anyRequest);

        assertThat(shouldRecord).isTrue();
    }

}
