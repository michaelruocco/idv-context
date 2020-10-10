package uk.co.idv.context.usecases.context.result;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import uk.co.idv.context.entities.context.Context;
import uk.co.idv.context.entities.context.lockout.ContextRecordAttemptRequest;
import uk.co.idv.context.entities.result.ServiceRecordResultResponse;
import uk.co.idv.context.entities.result.ServiceRecordResultRequest;
import uk.co.idv.context.entities.result.ServiceRecordResultRequestMother;
import uk.co.idv.context.entities.result.ServiceRecordResultResponseMother;
import uk.co.idv.context.usecases.context.ContextRepository;
import uk.co.idv.context.usecases.context.lockout.ContextLockoutService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class ResultServiceTest {

    private final ContextResultUpdater resultUpdater = mock(ContextResultUpdater.class);
    private final ContextRepository repository = mock(ContextRepository.class);
    private final ContextLockoutService lockoutService = mock(ContextLockoutService.class);

    private final ResultService service = ResultService.builder()
            .resultUpdater(resultUpdater)
            .repository(repository)
            .lockoutService(lockoutService)
            .build();

    @Test
    void shouldValidateLockoutStatus() {
        ServiceRecordResultRequest request = ServiceRecordResultRequestMother.build();
        ServiceRecordResultResponse response = ServiceRecordResultResponseMother.build();
        given(resultUpdater.addResultIfApplicable(request)).willReturn(response);

        service.record(request);

        ArgumentCaptor<Context> captor = ArgumentCaptor.forClass(Context.class);
        verify(lockoutService).validateLockoutState(captor.capture());
        assertThat(captor.getValue()).isEqualTo(request.getContext());
    }

    @Test
    void shouldReturnUpdatedContextAfterResultAdded() {
        ServiceRecordResultRequest request = ServiceRecordResultRequestMother.build();
        ServiceRecordResultResponse response = ServiceRecordResultResponseMother.build();
        given(resultUpdater.addResultIfApplicable(request)).willReturn(response);

        Context updated = service.record(request);

        assertThat(updated).isEqualTo(response.getUpdated());
    }

    @Test
    void shouldSaveUpdatedContext() {
        ServiceRecordResultRequest request = ServiceRecordResultRequestMother.build();
        ServiceRecordResultResponse response = ServiceRecordResultResponseMother.build();
        given(resultUpdater.addResultIfApplicable(request)).willReturn(response);

        service.record(request);

        ArgumentCaptor<Context> captor = ArgumentCaptor.forClass(Context.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue()).isEqualTo(response.getUpdated());
    }

    @Test
    void shouldPassResultToRecordAttemptOnLockoutService() {
        ServiceRecordResultRequest request = ServiceRecordResultRequestMother.build();
        ServiceRecordResultResponse response = ServiceRecordResultResponseMother.build();
        given(resultUpdater.addResultIfApplicable(request)).willReturn(response);

        service.record(request);

        ArgumentCaptor<ContextRecordAttemptRequest> captor = ArgumentCaptor.forClass(ContextRecordAttemptRequest.class);
        verify(lockoutService).recordAttemptIfRequired(captor.capture());
        ContextRecordAttemptRequest attemptRequest = captor.getValue();
        assertThat(attemptRequest.getResult()).isEqualTo(response.getResult());
    }

    @Test
    void shouldPassUpdatedContextToRecordAttemptOnLockoutService() {
        ServiceRecordResultRequest request = ServiceRecordResultRequestMother.build();
        ServiceRecordResultResponse response = ServiceRecordResultResponseMother.build();
        given(resultUpdater.addResultIfApplicable(request)).willReturn(response);

        service.record(request);

        ArgumentCaptor<ContextRecordAttemptRequest> captor = ArgumentCaptor.forClass(ContextRecordAttemptRequest.class);
        verify(lockoutService).recordAttemptIfRequired(captor.capture());
        ContextRecordAttemptRequest attemptRequest = captor.getValue();
        assertThat(attemptRequest.getContext()).isEqualTo(response.getUpdated());
    }

    @Test
    void shouldPassMethodCompleteToRecordAttemptOnLockoutService() {
        ServiceRecordResultRequest request = ServiceRecordResultRequestMother.build();
        ServiceRecordResultResponse response = ServiceRecordResultResponseMother.build();
        given(resultUpdater.addResultIfApplicable(request)).willReturn(response);

        service.record(request);

        ArgumentCaptor<ContextRecordAttemptRequest> captor = ArgumentCaptor.forClass(ContextRecordAttemptRequest.class);
        verify(lockoutService).recordAttemptIfRequired(captor.capture());
        ContextRecordAttemptRequest attemptRequest = captor.getValue();
        assertThat(attemptRequest.isMethodComplete()).isEqualTo(response.isMethodCompletedByResult());
    }

    @Test
    void shouldPassSequenceCompleteToRecordAttemptOnLockoutService() {
        ServiceRecordResultRequest request = ServiceRecordResultRequestMother.build();
        ServiceRecordResultResponse response = ServiceRecordResultResponseMother.build();
        given(resultUpdater.addResultIfApplicable(request)).willReturn(response);

        service.record(request);

        ArgumentCaptor<ContextRecordAttemptRequest> captor = ArgumentCaptor.forClass(ContextRecordAttemptRequest.class);
        verify(lockoutService).recordAttemptIfRequired(captor.capture());
        ContextRecordAttemptRequest attemptRequest = captor.getValue();
        assertThat(attemptRequest.isSequenceComplete()).isEqualTo(response.isSequenceCompletedByResult());
    }

}
