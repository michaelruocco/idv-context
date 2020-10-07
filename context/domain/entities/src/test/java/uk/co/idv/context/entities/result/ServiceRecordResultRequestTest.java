package uk.co.idv.context.entities.result;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.context.Context;
import uk.co.idv.method.entities.result.Result;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class ServiceRecordResultRequestTest {

    @Test
    void shouldReturnContext() {
        Context context = mock(Context.class);

        ServiceRecordResultRequest request = ServiceRecordResultRequest.builder()
                .context(context)
                .build();

        assertThat(request.getContext()).isEqualTo(context);
    }

    @Test
    void shouldReturnResult() {
        Result result = mock(Result.class);

        ServiceRecordResultRequest request = ServiceRecordResultRequest.builder()
                .result(result)
                .build();

        assertThat(request.getResult()).isEqualTo(result);
    }

    @Test
    void shouldReturnMethodNameFromResult() {
        String methodName = "method-name";
        Result result = mock(Result.class);
        given(result.getMethodName()).willReturn(methodName);

        ServiceRecordResultRequest request = ServiceRecordResultRequest.builder()
                .result(result)
                .build();

        assertThat(request.getMethodName()).isEqualTo(methodName);
    }

}
