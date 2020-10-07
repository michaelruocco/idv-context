package uk.co.idv.context.entities.result;

import lombok.Builder;
import lombok.Data;
import uk.co.idv.context.entities.context.Context;
import uk.co.idv.method.entities.result.Result;

@Builder
@Data
public class ServiceRecordResultRequest {

    private final Result result;
    private final Context context;

    public String getMethodName() {
        return result.getMethodName();
    }

}
