package uk.co.idv.context.usecases.context.result;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import uk.co.idv.context.entities.context.Context;
import uk.co.idv.context.entities.result.ServiceRecordResultRequest;
import uk.co.idv.context.entities.result.ServiceRecordResultRequestMother;

import java.util.function.UnaryOperator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class ContextResultUpdaterTest {

    private final ContextResultUpdater updater = new ContextResultUpdater();

    @Test
    void shouldThrowExceptionIfCannotAddResultForMethod() {
        Context context = mock(Context.class);
        ServiceRecordResultRequest request = ServiceRecordResultRequestMother.builder()
                .context(context)
                .build();
        given(context.hasNextEligibleIncompleteMethods(request.getMethodName())).willReturn(false);

        Throwable error = catchThrowable(() -> updater.addResultIfApplicable(request));

        assertThat(error)
                .isInstanceOf(CannotAddResultForMethodException.class)
                .hasMessage(request.getMethodName());
    }

    @Test
    void shouldReturnUpdatedRequest() {
        Context context = mock(Context.class);
        ServiceRecordResultRequest request = ServiceRecordResultRequestMother.builder()
                .context(context)
                .build();
        Context expectedUpdated = mock(Context.class);
        given(context.hasNextEligibleIncompleteMethods(request.getMethodName())).willReturn(true);
        given(context.apply(any(UnaryOperator.class))).willReturn(expectedUpdated);

        Context updated = updater.addResultIfApplicable(request);

        assertThat(updated).isEqualTo(expectedUpdated);
    }

    @Test
    void shouldApplyAddResultIfApplicableWithRequest() {
        Context context = mock(Context.class);
        ServiceRecordResultRequest request = ServiceRecordResultRequestMother.builder()
                .context(context)
                .build();
        Context expectedUpdated = mock(Context.class);
        given(context.hasNextEligibleIncompleteMethods(request.getMethodName())).willReturn(true);

        updater.addResultIfApplicable(request);

        ArgumentCaptor<AddResultIfApplicable> captor = ArgumentCaptor.forClass(AddResultIfApplicable.class);
        verify(context).apply(captor.capture());
        AddResultIfApplicable addResultFunction = captor.getValue();
        assertThat(addResultFunction.getRequest()).isEqualTo(request);
    }

}
