package uk.co.idv.lockout.entities.policy.recordattempt;

import org.junit.jupiter.api.Test;
import uk.co.idv.lockout.entities.policy.RecordAttemptPolicy;
import uk.co.idv.lockout.entities.policy.RecordAttemptRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class NeverRecordAttemptPolicyTest {

    private final RecordAttemptPolicy policy = new NeverRecordAttemptPolicy();

    @Test
    void shouldReturnType() {
        assertThat(policy.getType()).isEqualTo("never-record");
    }

    @Test
    void shouldNeverRecordAttempt() {
        RecordAttemptRequest anyRequest = mock(RecordAttemptRequest.class);

        boolean shouldRecord = policy.shouldRecordAttempt(anyRequest);

        assertThat(shouldRecord).isFalse();
    }

}
