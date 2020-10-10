package uk.co.idv.context.usecases.context;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import uk.co.idv.context.entities.context.Context;
import uk.co.idv.context.entities.context.create.CreateContextRequest;
import uk.co.idv.context.entities.context.create.FacadeCreateContextRequestMother;
import uk.co.idv.context.entities.context.create.ServiceCreateContextRequest;
import uk.co.idv.context.entities.result.FacadeRecordResultRequest;
import uk.co.idv.context.entities.result.FacadeRecordResultRequestMother;
import uk.co.idv.context.entities.result.ServiceRecordResultRequest;
import uk.co.idv.context.usecases.context.identity.IdentityLoader;
import uk.co.idv.context.usecases.context.result.ResultService;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class ContextFacadeTest {

    private final IdentityLoader identityLoader = mock(IdentityLoader.class);
    private final ContextService contextService = mock(ContextService.class);
    private final ResultService resultService = mock(ResultService.class);

    private final ContextFacade facade = ContextFacade.builder()
            .identityLoader(identityLoader)
            .contextService(contextService)
            .resultService(resultService)
            .build();

    @Test
    void shouldCreateContext() {
        CreateContextRequest request = FacadeCreateContextRequestMother.build();
        ServiceCreateContextRequest identityRequest = givenIdentityRequestLoaded(request);
        Context expectedContext = givenContextCreated(identityRequest);

        Context context = facade.create(request);

        assertThat(context).isEqualTo(expectedContext);
    }

    @Test
    void shouldFindContext() {
        UUID id = UUID.randomUUID();
        Context expectedContext = givenContextFound(id);

        Context context = facade.find(id);

        assertThat(context).isEqualTo(expectedContext);
    }

    @Test
    void shouldReturnUpdatedContext() {
        FacadeRecordResultRequest request = FacadeRecordResultRequestMother.build();
        Context expected = givenUpdatedContext();

        Context updated = facade.record(request);

        assertThat(updated).isEqualTo(expected);
    }

    @Test
    void shouldPassRequestWithLoadedContextToUpdateResult() {
        FacadeRecordResultRequest facadeRequest = FacadeRecordResultRequestMother.build();
        Context expected = givenContextFound(facadeRequest.getContextId());
        givenUpdatedContext();

        facade.record(facadeRequest);

        ArgumentCaptor<ServiceRecordResultRequest> captor = ArgumentCaptor.forClass(ServiceRecordResultRequest.class);
        verify(resultService).record(captor.capture());
        ServiceRecordResultRequest serviceRequest = captor.getValue();
        assertThat(serviceRequest.getContext()).isEqualTo(expected);
    }

    @Test
    void shouldPassRequestWithResultToUpdateResult() {
        FacadeRecordResultRequest facadeRequest = FacadeRecordResultRequestMother.build();
        givenUpdatedContext();

        facade.record(facadeRequest);

        ArgumentCaptor<ServiceRecordResultRequest> captor = ArgumentCaptor.forClass(ServiceRecordResultRequest.class);
        verify(resultService).record(captor.capture());
        ServiceRecordResultRequest serviceRequest = captor.getValue();
        assertThat(serviceRequest.getResult()).isEqualTo(facadeRequest.getResult());
    }

    private ServiceCreateContextRequest givenIdentityRequestLoaded(CreateContextRequest initialRequest) {
        ServiceCreateContextRequest identityRequest = mock(ServiceCreateContextRequest.class);
        given(identityLoader.addIdentity(initialRequest)).willReturn(identityRequest);
        return identityRequest;
    }

    private Context givenContextCreated(ServiceCreateContextRequest request) {
        Context context = mock(Context.class);
        given(contextService.create(request)).willReturn(context);
        return context;
    }

    private Context givenContextFound(UUID id) {
        Context context = mock(Context.class);
        given(contextService.find(id)).willReturn(context);
        return context;
    }

    private Context givenUpdatedContext() {
        Context context = mock(Context.class);
        given(resultService.record(any(ServiceRecordResultRequest.class))).willReturn(context);
        return context;
    }

}
