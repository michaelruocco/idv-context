package uk.co.idv.context.usecases.context.result;

import lombok.Builder;
import lombok.Data;
import uk.co.idv.context.entities.result.ServiceRecordResultRequest;
import uk.co.idv.method.entities.method.Method;

import java.util.function.UnaryOperator;

@Builder
@Data
public class AddResultIfApplicable implements UnaryOperator<Method> {

    private final ServiceRecordResultRequest request;

    @Override
    public Method apply(Method method) {
        if (isApplicable(method)) {
            return method.add(request.getResult());
        }
        return method;
    }

    private boolean isApplicable(Method method) {
        return method.hasName(request.getMethodName())
                && method.isEligible()
                && !method.isComplete();
    }

}
