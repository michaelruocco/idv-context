package uk.co.idv.context.usecases.context.result;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import uk.co.idv.context.entities.context.Context;
import uk.co.idv.context.entities.result.ServiceRecordResultRequest;
import uk.co.idv.context.usecases.context.ContextRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class ResultServiceTest {

    private final ContextResultUpdater resultUpdater = mock(ContextResultUpdater.class);
    private final ContextRepository repository = mock(ContextRepository.class);

    private final ResultService service = ResultService.builder()
            .resultUpdater(resultUpdater)
            .repository(repository)
            .build();

    @Test
    void shouldReturnContextAfterResultAdded() {
        ServiceRecordResultRequest request = mock(ServiceRecordResultRequest.class);
        Context expected = mock(Context.class);
        given(resultUpdater.addResultIfApplicable(request)).willReturn(expected);

        Context updated = service.record(request);

        assertThat(updated).isEqualTo(updated);
    }

    @Test
    void shouldSaveUpdatedContext() {
        ServiceRecordResultRequest request = mock(ServiceRecordResultRequest.class);
        Context expected = mock(Context.class);
        given(resultUpdater.addResultIfApplicable(request)).willReturn(expected);

        service.record(request);

        ArgumentCaptor<Context> captor = ArgumentCaptor.forClass(Context.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue()).isEqualTo(expected);
    }

}
