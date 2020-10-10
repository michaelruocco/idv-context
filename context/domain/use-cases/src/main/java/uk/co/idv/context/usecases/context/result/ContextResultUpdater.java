package uk.co.idv.context.usecases.context.result;

import uk.co.idv.context.entities.context.Context;
import uk.co.idv.context.entities.result.ServiceRecordResultRequest;
import uk.co.idv.context.entities.context.HasEligibleMethod;
import uk.co.idv.context.entities.context.HasNextMethod;
import uk.co.idv.context.entities.result.ServiceRecordResultResponse;

public class ContextResultUpdater {

    public ServiceRecordResultResponse addResultIfApplicable(ServiceRecordResultRequest request) {
        validateMethodIsNextAndEligible(request);
        Context original = request.getContext();
        Context updated = original.updateMethods(new AddResultIfApplicable(request));
        return ServiceRecordResultResponse.builder()
                .result(request.getResult())
                .original(original)
                .updated(updated)
                .build();
    }

    private void validateMethodIsNextAndEligible(ServiceRecordResultRequest request) {
        Context original = request.getContext();
        String methodName = request.getMethodName();
        if (!original.query(new HasNextMethod(methodName))) {
            throw new NotNextMethodException(methodName);
        }
        if (!original.query(new HasEligibleMethod(methodName))) {
            throw new MethodNotEligibleException(methodName);
        }
    }

}
