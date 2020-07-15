package uk.co.idv.context.adapter.repository;

import com.amazonaws.services.dynamodbv2.document.BatchWriteItemOutcome;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class DynamoBatchWriteExceptionTest {

    private final BatchWriteItemOutcome outcome = mock(BatchWriteItemOutcome.class);

    @Test
    void shouldReturnMessage() {
        String expectedMessage = "expected-message";
        given(outcome.toString()).willReturn(expectedMessage);
        Throwable exception = new DynamoBatchWriteException(outcome);

        String message = exception.getMessage();

        assertThat(message).isEqualTo(expectedMessage);
    }

    @Test
    void shouldReturnOutcome() {
        DynamoBatchWriteException exception = new DynamoBatchWriteException(outcome);

        assertThat(exception.getOutcome()).isEqualTo(outcome);
    }

}
