package uk.co.idv.context.entities.lockout.policy.recordattempt;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.lockout.policy.RecordAttemptPolicy;
import uk.co.idv.context.entities.lockout.policy.RecordAttemptRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class RecordAttemptWhenSequenceCompletePolicyTest {

    private final RecordAttemptPolicy policy = new RecordAttemptWhenSequenceCompletePolicy();

    @Test
    void shouldReturnType() {
        assertThat(policy.getType()).isEqualTo("record-when-sequence-complete");
    }

    @Test
    void shouldRecordAttemptIfSequenceIsComplete() {
        RecordAttemptRequest request = mock(RecordAttemptRequest.class);
        given(request.isSequenceComplete()).willReturn(true);

        boolean shouldRecord = policy.shouldRecordAttempt(request);

        assertThat(shouldRecord).isTrue();
    }

    @Test
    void shouldNotRecordAttemptIfSequenceIsNotComplete() {
        RecordAttemptRequest request = mock(RecordAttemptRequest.class);
        given(request.isSequenceComplete()).willReturn(false);

        boolean shouldRecord = policy.shouldRecordAttempt(request);

        assertThat(shouldRecord).isFalse();
    }

}
