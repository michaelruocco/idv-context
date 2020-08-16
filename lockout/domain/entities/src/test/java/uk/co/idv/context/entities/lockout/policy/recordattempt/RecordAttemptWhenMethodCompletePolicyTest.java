package uk.co.idv.context.entities.lockout.policy.recordattempt;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.lockout.policy.RecordAttemptPolicy;
import uk.co.idv.context.entities.lockout.policy.RecordAttemptRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class RecordAttemptWhenMethodCompletePolicyTest {

    private final RecordAttemptPolicy policy = new RecordAttemptWhenMethodCompletePolicy();

    @Test
    void shouldReturnType() {
        assertThat(policy.getType()).isEqualTo("record-when-method-complete");
    }

    @Test
    void shouldRecordAttemptIfMethodIsComplete() {
        RecordAttemptRequest request = mock(RecordAttemptRequest.class);
        given(request.isMethodComplete()).willReturn(true);

        boolean shouldRecord = policy.shouldRecordAttempt(request);

        assertThat(shouldRecord).isTrue();
    }

    @Test
    void shouldNotRecordAttemptIfMethodIsNotComplete() {
        RecordAttemptRequest request = mock(RecordAttemptRequest.class);
        given(request.isMethodComplete()).willReturn(false);

        boolean shouldRecord = policy.shouldRecordAttempt(request);

        assertThat(shouldRecord).isFalse();
    }

}
