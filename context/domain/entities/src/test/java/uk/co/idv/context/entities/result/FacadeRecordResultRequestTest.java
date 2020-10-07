package uk.co.idv.context.entities.result;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.context.Context;
import uk.co.idv.method.entities.result.Result;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class FacadeRecordResultRequestTest {

    @Test
    void shouldReturnContextId() {
        UUID contextId = UUID.randomUUID();

        FacadeRecordResultRequest request = FacadeRecordResultRequest.builder()
                .contextId(contextId)
                .build();

        assertThat(request.getContextId()).isEqualTo(contextId);
    }

    @Test
    void shouldReturnResult() {
        Result result = mock(Result.class);

        FacadeRecordResultRequest request = FacadeRecordResultRequest.builder()
                .result(result)
                .build();

        assertThat(request.getResult()).isEqualTo(result);
    }

    @Test
    void shouldPopulateResultOnServiceRequest() {
        Result result = mock(Result.class);
        FacadeRecordResultRequest facadeRequest = FacadeRecordResultRequest.builder()
                .result(result)
                .build();
        Context context = mock(Context.class);

        ServiceRecordResultRequest request = facadeRequest.toServiceRequest(context);

        assertThat(request.getResult()).isEqualTo(result);
    }

    @Test
    void shouldContextOnServiceRequest() {
        FacadeRecordResultRequest facadeRequest = FacadeRecordResultRequest.builder()
                .build();
        Context context = mock(Context.class);

        ServiceRecordResultRequest request = facadeRequest.toServiceRequest(context);

        assertThat(request.getContext()).isEqualTo(context);
    }

}
