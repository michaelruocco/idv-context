package uk.co.idv.context.usecases.context.result;

import lombok.Builder;
import uk.co.idv.context.entities.context.Context;
import uk.co.idv.context.entities.context.lockout.ContextRecordAttemptRequest;
import uk.co.idv.context.entities.result.ServiceRecordResultResponse;
import uk.co.idv.context.entities.result.ServiceRecordResultRequest;
import uk.co.idv.context.usecases.context.ContextRepository;
import uk.co.idv.context.usecases.context.lockout.ContextLockoutService;

@Builder
public class ResultService {

    @Builder.Default
    private final ContextResultUpdater resultUpdater = new ContextResultUpdater();

    private final ContextRepository repository;
    private final ContextLockoutService lockoutService;

    public Context record(ServiceRecordResultRequest request) {
        lockoutService.validateLockoutState(request.getContext());
        ServiceRecordResultResponse response = resultUpdater.addResultIfApplicable(request);
        repository.save(response.getUpdated());
        lockoutService.recordAttemptIfRequired(toRecordAttemptRequest(response));
        return response.getUpdated();
    }

    private ContextRecordAttemptRequest toRecordAttemptRequest(ServiceRecordResultResponse response) {
        return ContextRecordAttemptRequest.builder()
                .result(response.getResult())
                .context(response.getUpdated())
                .methodComplete(response.isMethodCompletedByResult())
                .sequenceComplete(response.isSequenceCompletedByResult())
                .build();
    }

}
