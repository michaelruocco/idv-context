package uk.co.idv.context.usecases.context;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.context.Context;
import uk.co.idv.context.entities.context.ContextMother;
import uk.co.idv.context.entities.context.EligibleMethodsContext;
import uk.co.idv.context.entities.context.EligibleMethodsContextRequest;
import uk.co.idv.context.entities.context.EligibleMethodsContextRequestMother;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class EligibleMethodsServiceTest {

    private final ContextService contextService = mock(ContextService.class);

    private final EligibleMethodsService methodsService = new EligibleMethodsService(contextService);

    @Test
    void shouldPopulateContextId() {
        EligibleMethodsContextRequest request = EligibleMethodsContextRequestMother.build();
        Context context = ContextMother.build();
        given(contextService.findWithEligibleIncompleteMethods(request.getContextId())).willReturn(context);

        EligibleMethodsContext methodsContext = methodsService.find(request);

        assertThat(methodsContext.getId()).isEqualTo(context.getId());
    }

    @Test
    void shouldPopulateActivity() {
        EligibleMethodsContextRequest request = EligibleMethodsContextRequestMother.build();
        Context context = ContextMother.build();
        given(contextService.findWithEligibleIncompleteMethods(request.getContextId())).willReturn(context);

        EligibleMethodsContext methodsContext = methodsService.find(request);

        assertThat(methodsContext.getActivity()).isEqualTo(context.getActivity());
    }

    @Test
    void shouldPopulateProtectSensitiveData() {
        EligibleMethodsContextRequest request = EligibleMethodsContextRequestMother.build();
        Context context = ContextMother.build();
        given(contextService.findWithEligibleIncompleteMethods(request.getContextId())).willReturn(context);

        EligibleMethodsContext methodsContext = methodsService.find(request);

        assertThat(methodsContext.isProtectSensitiveData()).isEqualTo(context.isProtectSensitiveData());
    }

    @Test
    void shouldPopulateNextMethods() {
        EligibleMethodsContextRequest request = EligibleMethodsContextRequestMother.build();
        Context context = ContextMother.build();
        given(contextService.findWithEligibleIncompleteMethods(request.getContextId())).willReturn(context);

        EligibleMethodsContext methodsContext = methodsService.find(request);

        assertThat(methodsContext.getMethods()).isEqualTo(context.getNextMethods(request.getMethodName()));
    }

}
