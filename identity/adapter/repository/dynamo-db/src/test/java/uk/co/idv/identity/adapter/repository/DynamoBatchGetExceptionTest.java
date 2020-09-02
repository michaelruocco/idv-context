package uk.co.idv.identity.adapter.repository;

import com.amazonaws.services.dynamodbv2.document.BatchGetItemOutcome;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class DynamoBatchGetExceptionTest {

    private final BatchGetItemOutcome outcome = mock(BatchGetItemOutcome.class);

    @Test
    void shouldReturnMessage() {
        String expectedMessage = "expected-message";
        given(outcome.toString()).willReturn(expectedMessage);
        Throwable exception = new DynamoBatchGetException(outcome);

        String message = exception.getMessage();

        assertThat(message).isEqualTo(expectedMessage);
    }

    @Test
    void shouldReturnOutcome() {
        DynamoBatchGetException exception = new DynamoBatchGetException(outcome);

        assertThat(exception.getOutcome()).isEqualTo(outcome);
    }

}
