package uk.co.idv.context.entities.context.lockout;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.context.Context;
import uk.co.idv.context.entities.context.ContextMother;
import uk.co.idv.lockout.entities.attempt.Attempt;
import uk.co.idv.method.entities.result.Result;
import uk.co.idv.method.entities.result.ResultMother;

import static org.assertj.core.api.Assertions.assertThat;

class ContextRecordAttemptRequestTest {

    private static final Result RESULT = ResultMother.build();
    private static final Context CONTEXT = ContextMother.build();

    private final ContextRecordAttemptRequest request = ContextRecordAttemptRequest.builder()
            .result(RESULT)
            .context(CONTEXT)
            .methodComplete(true)
            .sequenceComplete(true)
            .build();

    @Test
    void shouldReturnResult() {
        assertThat(request.getResult()).isEqualTo(RESULT);
    }

    @Test
    void shouldReturnContext() {
        assertThat(request.getContext()).isEqualTo(CONTEXT);
    }

    @Test
    void shouldReturnSequenceComplete() {
        assertThat(request.isSequenceComplete()).isTrue();
    }

    @Test
    void shouldReturnMethodComplete() {
        assertThat(request.isMethodComplete()).isTrue();
    }

    @Test
    void shouldPopulateActivityNameOnAttempt() {
        Attempt attempt = request.getAttempt();

        assertThat(attempt.getActivityName()).isEqualTo(CONTEXT.getActivityName());
    }

    @Test
    void shouldReturnActivityName() {
        assertThat(request.getActivityName()).isEqualTo(CONTEXT.getActivityName());
    }

    @Test
    void shouldPopulateAliasesOnAttempt() {
        Attempt attempt = request.getAttempt();

        assertThat(attempt.getAliases()).isEqualTo(CONTEXT.getAliases());
    }

    @Test
    void shouldReturnAliases() {
        assertThat(request.getAliases()).isEqualTo(CONTEXT.getAliases());
    }

    @Test
    void shouldReturnAliasTypes() {
        assertThat(request.getAliasTypes()).isEqualTo(CONTEXT.getAliasTypes());
    }

    @Test
    void shouldPopulateChannelIdOnAttempt() {
        Attempt attempt = request.getAttempt();

        assertThat(attempt.getChannelId()).isEqualTo(CONTEXT.getChannelId());
    }

    @Test
    void shouldReturnChannelId() {
        assertThat(request.getChannelId()).isEqualTo(CONTEXT.getChannelId());
    }

    @Test
    void shouldPopulateIdvIdOnAttempt() {
        Attempt attempt = request.getAttempt();

        assertThat(attempt.getIdvId()).isEqualTo(CONTEXT.getIdvId());
    }

    @Test
    void shouldReturnPopulateIdvId() {
        assertThat(request.getIdvId()).isEqualTo(CONTEXT.getIdvId());
    }

    @Test
    void shouldPopulateContextIdOnAttempt() {
        Attempt attempt = request.getAttempt();

        assertThat(attempt.getContextId()).isEqualTo(CONTEXT.getId());
    }

    @Test
    void shouldPopulateMethodNameOnAttempt() {
        Attempt attempt = request.getAttempt();

        assertThat(attempt.getMethodName()).isEqualTo(RESULT.getMethodName());
    }

    @Test
    void shouldPopulateSuccessfulOnAttempt() {
        Attempt attempt = request.getAttempt();

        assertThat(attempt.isSuccessful()).isEqualTo(RESULT.isSuccessful());
    }

    @Test
    void shouldPopulateTimestampOnAttempt() {
        Attempt attempt = request.getAttempt();

        assertThat(attempt.getTimestamp()).isEqualTo(RESULT.getTimestamp());
    }

    @Test
    void shouldReturnTimestamp() {
        assertThat(request.getTimestamp()).isEqualTo(RESULT.getTimestamp());
    }

    @Test
    void shouldPopulateVerificationIdOnAttempt() {
        Attempt attempt = request.getAttempt();

        assertThat(attempt.getVerificationId()).isEqualTo(RESULT.getVerificationId());
    }

}
