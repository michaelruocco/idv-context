package uk.co.idv.context.usecases.context.result;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.result.ServiceRecordResultRequest;
import uk.co.idv.context.entities.result.ServiceRecordResultRequestMother;
import uk.co.idv.method.entities.method.Method;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

class AddResultIfApplicableTest {

    private final ServiceRecordResultRequest request = ServiceRecordResultRequestMother.build();

    private final AddResultIfApplicable function = new AddResultIfApplicable(request);

    @Test
    void shouldNotAddResultIfMethodNameDoesNotMatchRequestMethodName() {
        Method method = mock(Method.class);
        given(method.hasName(request.getMethodName())).willReturn(false);

        Method updated = function.apply(method);

        assertThat(updated).isEqualTo(method);
        verify(method, never()).add(request.getResult());
    }

    @Test
    void shouldNotAddResultIfMethodIsNotEligible() {
        Method method = mock(Method.class);
        given(method.hasName(request.getMethodName())).willReturn(true);
        given(method.isEligible()).willReturn(false);

        Method updated = function.apply(method);

        assertThat(updated).isEqualTo(method);
        verify(method, never()).add(request.getResult());
    }

    @Test
    void shouldNotAddResultIfMethodIsComplete() {
        Method method = mock(Method.class);
        given(method.hasName(request.getMethodName())).willReturn(true);
        given(method.isEligible()).willReturn(true);
        given(method.isComplete()).willReturn(true);

        Method updated = function.apply(method);

        assertThat(updated).isEqualTo(method);
        verify(method, never()).add(request.getResult());
    }

    @Test
    void shouldAddResultIfMethodHasResultMethodNameIsEligibleAndIncomplete() {
        Method method = mock(Method.class);
        given(method.hasName(request.getMethodName())).willReturn(true);
        given(method.isEligible()).willReturn(true);
        given(method.isComplete()).willReturn(false);
        Method expected = mock(Method.class);
        given(method.add(request.getResult())).willReturn(expected);

        Method updated = function.apply(method);

        assertThat(updated).isEqualTo(expected);
    }

}
