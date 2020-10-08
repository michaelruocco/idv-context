package uk.co.idv.context.usecases.context.result;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import uk.co.idv.context.entities.context.Context;
import uk.co.idv.context.entities.context.HasEligibleMethod;
import uk.co.idv.context.entities.context.HasNextMethod;
import uk.co.idv.context.entities.result.ServiceRecordResultRequest;
import uk.co.idv.context.entities.result.ServiceRecordResultRequestMother;
import uk.co.idv.context.usecases.context.MethodNotEligibleException;
import uk.co.idv.context.usecases.context.NotNextMethodException;

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
        Context context = mock(Context.class);
        ServiceRecordResultRequest request = ServiceRecordResultRequestMother.builder()
                .context(context)
                .build();
        given(context.query(any(HasNextMethod.class))).willReturn(false);

        Throwable error = catchThrowable(() -> updater.addResultIfApplicable(request));

        assertThat(error)
                .isInstanceOf(NotNextMethodException.class)
                .hasMessage(request.getMethodName());
    }

    @Test
    void shouldPassMethodNameWhenCheckingHasNextMethod() {
        Context context = mock(Context.class);
        ServiceRecordResultRequest request = ServiceRecordResultRequestMother.builder()
                .context(context)
                .build();
        given(context.query(any(HasNextMethod.class))).willReturn(true);
        given(context.query(any(HasEligibleMethod.class))).willReturn(true);

        updater.addResultIfApplicable(request);

        ArgumentCaptor<HasNextMethod> captor = ArgumentCaptor.forClass(HasNextMethod.class);
        verify(context, times(2)).query(captor.capture());
        HasNextMethod hasNextMethod = captor.getAllValues().get(0);
        assertThat(hasNextMethod.getMethodName()).isEqualTo(request.getMethodName());
    }

    @Test
    void shouldThrowExceptionIfMethodNotEligible() {
        Context context = mock(Context.class);
        ServiceRecordResultRequest request = ServiceRecordResultRequestMother.builder()
                .context(context)
                .build();
        given(context.query(any(HasNextMethod.class))).willReturn(true);
        given(context.query(any(HasEligibleMethod.class))).willReturn(false);

        Throwable error = catchThrowable(() -> updater.addResultIfApplicable(request));

        assertThat(error)
                .isInstanceOf(MethodNotEligibleException.class)
                .hasMessage(request.getMethodName());
    }

    @Test
    void shouldPassMethodNameWhenCheckingHasEligibleMethod() {
        Context context = mock(Context.class);
        ServiceRecordResultRequest request = ServiceRecordResultRequestMother.builder()
                .context(context)
                .build();
        given(context.query(any(HasNextMethod.class))).willReturn(true);
        given(context.query(any(HasEligibleMethod.class))).willReturn(true);

        updater.addResultIfApplicable(request);

        ArgumentCaptor<HasEligibleMethod> captor = ArgumentCaptor.forClass(HasEligibleMethod.class);
        verify(context, times(2)).query(captor.capture());
        HasEligibleMethod hasEligibleMethod = captor.getAllValues().get(1);
        assertThat(hasEligibleMethod.getMethodName()).isEqualTo(request.getMethodName());
    }

    @Test
    void shouldReturnUpdatedRequest() {
        Context context = mock(Context.class);
        ServiceRecordResultRequest request = ServiceRecordResultRequestMother.builder()
                .context(context)
                .build();
        Context expectedUpdated = mock(Context.class);
        given(context.query(any(HasNextMethod.class))).willReturn(true);
        given(context.query(any(HasEligibleMethod.class))).willReturn(true);
        given(context.updateMethods(any(UnaryOperator.class))).willReturn(expectedUpdated);

        Context updated = updater.addResultIfApplicable(request);

        assertThat(updated).isEqualTo(expectedUpdated);
    }

    @Test
    void shouldApplyAddResultIfApplicableWithRequest() {
        Context context = mock(Context.class);
        ServiceRecordResultRequest request = ServiceRecordResultRequestMother.builder()
                .context(context)
                .build();
        given(context.query(any(HasNextMethod.class))).willReturn(true);
        given(context.query(any(HasEligibleMethod.class))).willReturn(true);

        updater.addResultIfApplicable(request);

        ArgumentCaptor<AddResultIfApplicable> captor = ArgumentCaptor.forClass(AddResultIfApplicable.class);
        verify(context).updateMethods(captor.capture());
        AddResultIfApplicable addResultFunction = captor.getValue();
        assertThat(addResultFunction.getRequest()).isEqualTo(request);
    }

}
