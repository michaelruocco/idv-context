package uk.co.idv.context.usecases.context.result;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import uk.co.idv.context.entities.context.Context;
import uk.co.idv.context.entities.context.HasEligibleMethod;
import uk.co.idv.context.entities.context.HasNextMethod;
import uk.co.idv.context.entities.result.ServiceRecordResultRequest;
import uk.co.idv.context.entities.result.ServiceRecordResultRequestMother;
import uk.co.idv.context.entities.result.ServiceRecordResultResponse;
import uk.co.idv.method.entities.result.Result;
import uk.co.idv.method.entities.result.ResultMother;

import java.util.function.UnaryOperator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class ContextResultUpdaterTest {

    private final ContextResultUpdater updater = new ContextResultUpdater();

    @Test
    void shouldThrowExceptionIfNotNextMethod() {
        Context original = mock(Context.class);
        ServiceRecordResultRequest request = ServiceRecordResultRequestMother.builder()
                .context(original)
                .build();
        given(original.query(any(HasNextMethod.class))).willReturn(false);

        Throwable error = catchThrowable(() -> updater.addResultIfApplicable(request));

        assertThat(error)
                .isInstanceOf(NotNextMethodException.class)
                .hasMessage(request.getMethodName());
    }

    @Test
    void shouldPassMethodNameWhenCheckingHasNextMethod() {
        Context original = mock(Context.class);
        givenContextUpdatedSuccessfully(original);
        ServiceRecordResultRequest request = ServiceRecordResultRequestMother.builder()
                .context(original)
                .build();

        updater.addResultIfApplicable(request);

        ArgumentCaptor<HasNextMethod> captor = ArgumentCaptor.forClass(HasNextMethod.class);
        verify(original, times(2)).query(captor.capture());
        HasNextMethod hasNextMethod = captor.getAllValues().get(0);
        assertThat(hasNextMethod.getMethodName()).isEqualTo(request.getMethodName());
    }

    @Test
    void shouldThrowExceptionIfMethodNotEligible() {
        Context original = mock(Context.class);
        ServiceRecordResultRequest request = ServiceRecordResultRequestMother.builder()
                .context(original)
                .build();
        given(original.query(any(HasNextMethod.class))).willReturn(true);
        given(original.query(any(HasEligibleMethod.class))).willReturn(false);

        Throwable error = catchThrowable(() -> updater.addResultIfApplicable(request));

        assertThat(error)
                .isInstanceOf(MethodNotEligibleException.class)
                .hasMessage(request.getMethodName());
    }

    @Test
    void shouldPassMethodNameWhenCheckingHasEligibleMethod() {
        Context original = mock(Context.class);
        givenContextUpdatedSuccessfully(original);
        ServiceRecordResultRequest request = ServiceRecordResultRequestMother.builder()
                .context(original)
                .build();

        updater.addResultIfApplicable(request);

        ArgumentCaptor<HasEligibleMethod> captor = ArgumentCaptor.forClass(HasEligibleMethod.class);
        verify(original, times(2)).query(captor.capture());
        HasEligibleMethod hasEligibleMethod = captor.getAllValues().get(1);
        assertThat(hasEligibleMethod.getMethodName()).isEqualTo(request.getMethodName());
    }

    @Test
    void shouldReturnUpdatedContext() {
        Context original = mock(Context.class);
        Context expected = givenContextUpdatedSuccessfully(original);
        ServiceRecordResultRequest request = ServiceRecordResultRequestMother.builder()
                .context(original)
                .build();

        ServiceRecordResultResponse response = updater.addResultIfApplicable(request);

        assertThat(response.getUpdated()).isEqualTo(expected);
    }

    @Test
    void shouldApplyAddResultIfApplicableWithRequest() {
        Context original = mock(Context.class);
        givenContextUpdatedSuccessfully(original);
        ServiceRecordResultRequest request = ServiceRecordResultRequestMother.builder()
                .context(original)
                .build();

        updater.addResultIfApplicable(request);

        ArgumentCaptor<AddResultIfApplicable> captor = ArgumentCaptor.forClass(AddResultIfApplicable.class);
        verify(original).updateMethods(captor.capture());
        AddResultIfApplicable addResultFunction = captor.getValue();
        assertThat(addResultFunction.getRequest()).isEqualTo(request);
    }

    @Test
    void shouldReturnOriginalContext() {
        Context original = mock(Context.class);
        givenContextUpdatedSuccessfully(original);
        ServiceRecordResultRequest request = ServiceRecordResultRequestMother.builder()
                .context(original)
                .build();

        ServiceRecordResultResponse response = updater.addResultIfApplicable(request);

        assertThat(response.getOriginal()).isEqualTo(original);
    }

    @Test
    void shouldReturnResultFromRequest() {
        Context original = mock(Context.class);
        Result result = ResultMother.build();
        givenContextUpdatedSuccessfully(original);
        ServiceRecordResultRequest request = ServiceRecordResultRequestMother.builder()
                .context(original)
                .result(result)
                .build();

        ServiceRecordResultResponse response = updater.addResultIfApplicable(request);

        assertThat(response.getResult()).isEqualTo(result);
    }

    private Context givenContextUpdatedSuccessfully(Context original) {
        Context updated = mock(Context.class);
        given(original.query(any(HasNextMethod.class))).willReturn(true);
        given(original.query(any(HasEligibleMethod.class))).willReturn(true);
        given(original.updateMethods(any(UnaryOperator.class))).willReturn(updated);
        return updated;
    }

}
