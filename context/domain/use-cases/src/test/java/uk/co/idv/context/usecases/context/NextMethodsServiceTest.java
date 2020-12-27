package uk.co.idv.context.usecases.context;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.context.Context;
import uk.co.idv.context.entities.context.ContextMother;
import uk.co.idv.context.entities.context.NextMethods;
import uk.co.idv.context.entities.context.NextMethodsRequest;
import uk.co.idv.context.entities.context.NextMethodsRequestMother;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class NextMethodsServiceTest {

    private final ContextService contextService = mock(ContextService.class);

    private final NextMethodsService methodsService = new NextMethodsService(contextService);

    @Test
    void shouldPopulateContextId() {
        NextMethodsRequest request = NextMethodsRequestMother.build();
        Context context = ContextMother.build();
        given(contextService.findWithEligibleIncompleteSequences(request.getContextId())).willReturn(context);

        NextMethods methodsContext = methodsService.find(request);

        assertThat(methodsContext.getId()).isEqualTo(context.getId());
    }

    @Test
    void shouldPopulateActivity() {
        NextMethodsRequest request = NextMethodsRequestMother.build();
        Context context = ContextMother.build();
        given(contextService.findWithEligibleIncompleteSequences(request.getContextId())).willReturn(context);

        NextMethods methodsContext = methodsService.find(request);

        assertThat(methodsContext.getActivity()).isEqualTo(context.getActivity());
    }

    @Test
    void shouldPopulateProtectSensitiveData() {
        NextMethodsRequest request = NextMethodsRequestMother.build();
        Context context = ContextMother.build();
        given(contextService.findWithEligibleIncompleteSequences(request.getContextId())).willReturn(context);

        NextMethods methodsContext = methodsService.find(request);

        assertThat(methodsContext.isProtectSensitiveData()).isEqualTo(context.isProtectSensitiveData());
    }

    @Test
    void shouldPopulateNextMethods() {
        NextMethodsRequest request = NextMethodsRequestMother.build();
        Context context = ContextMother.build();
        given(contextService.findWithEligibleIncompleteSequences(request.getContextId())).willReturn(context);

        NextMethods methodsContext = methodsService.find(request);

        assertThat(methodsContext.getMethods()).isEqualTo(context.getNextMethods(request.getMethodName()));
    }

}
