package uk.co.idv.context.usecases.context.result;

import uk.co.idv.context.entities.context.Context;
import uk.co.idv.context.entities.result.ServiceRecordResultRequest;

public class ContextResultUpdater {

    public Context addResultIfApplicable(ServiceRecordResultRequest request) {
        Context context = request.getContext();
        if (!context.hasNextEligibleIncompleteMethods(request.getMethodName())) {
            throw new CannotAddResultForMethodException(request.getMethodName());
        }
        return context.apply(new AddResultIfApplicable(request));
    }

}
