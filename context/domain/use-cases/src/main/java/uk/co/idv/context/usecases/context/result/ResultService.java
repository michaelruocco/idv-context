package uk.co.idv.context.usecases.context.result;

import lombok.Builder;
import uk.co.idv.context.entities.context.Context;
import uk.co.idv.context.entities.result.ServiceRecordResultRequest;
import uk.co.idv.context.usecases.context.ContextRepository;

@Builder
public class ResultService {

    private final ContextResultUpdater resultUpdater;
    private final ContextRepository repository;

    public Context record(ServiceRecordResultRequest request) {
        Context context = resultUpdater.addResultIfApplicable(request);
        repository.save(context);
        return context;
    }

}
