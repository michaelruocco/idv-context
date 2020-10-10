package uk.co.idv.context.entities.result;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.context.Context;
import uk.co.idv.method.entities.result.Result;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class ServiceRecordResultResponseTest {

    @Test
    void shouldReturnOriginalContext() {
        Context original = mock(Context.class);

        ServiceRecordResultResponse response = ServiceRecordResultResponse.builder()
                .original(original)
                .build();

        assertThat(response.getOriginal()).isEqualTo(original);
    }

    @Test
    void shouldReturnUpdatedContext() {
        Context updated = mock(Context.class);

        ServiceRecordResultResponse response = ServiceRecordResultResponse.builder()
                .updated(updated)
                .build();

        assertThat(response.getUpdated()).isEqualTo(updated);
    }

    @Test
    void shouldReturnResultContext() {
        Result result = mock(Result.class);

        ServiceRecordResultResponse response = ServiceRecordResultResponse.builder()
                .result(result)
                .build();

        assertThat(response.getResult()).isEqualTo(result);
    }

    @Test
    void shouldReturnIsSequenceCompletedByResult() {
        Context original = mock(Context.class);
        Context updated = mock(Context.class);
        given(updated.hasMoreCompletedSequencesThan(original)).willReturn(true);

        ServiceRecordResultResponse response = ServiceRecordResultResponse.builder()
                .original(original)
                .updated(updated)
                .build();

        assertThat(response.isSequenceCompletedByResult()).isTrue();
    }

    @Test
    void shouldReturnIsMethodCompletedByResult() {
        Context original = mock(Context.class);
        Context updated = mock(Context.class);
        given(updated.hasMoreCompletedMethodsThan(original)).willReturn(true);

        ServiceRecordResultResponse response = ServiceRecordResultResponse.builder()
                .original(original)
                .updated(updated)
                .build();

        assertThat(response.isMethodCompletedByResult()).isTrue();
    }

}
