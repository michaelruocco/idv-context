package uk.co.idv.context.usecases.context.result;

import uk.co.idv.context.entities.context.Context;
import uk.co.idv.context.entities.result.ServiceRecordResultRequest;
import uk.co.idv.context.entities.context.HasEligibleMethod;
import uk.co.idv.context.entities.context.HasNextMethod;
import uk.co.idv.context.usecases.context.MethodNotEligibleException;
import uk.co.idv.context.usecases.context.NotNextMethodException;

public class ContextResultUpdater {

    public Context addResultIfApplicable(ServiceRecordResultRequest request) {
        Context context = request.getContext();
        String methodName = request.getMethodName();
        if (!context.query(new HasNextMethod(methodName))) {
            throw new NotNextMethodException(methodName);
        }
        if (!context.query(new HasEligibleMethod(methodName))) {
            throw new MethodNotEligibleException(methodName);
        }
        return context.updateMethods(new AddResultIfApplicable(request));
    }

}
