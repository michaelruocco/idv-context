package uk.co.idv.context.entities.result;

import lombok.Builder;
import lombok.Data;
import uk.co.idv.context.entities.context.Context;
import uk.co.idv.method.entities.result.Result;

import java.util.UUID;

@Builder
@Data
public class FacadeRecordResultRequest {

    private final UUID contextId;
    private final Result result;

    public ServiceRecordResultRequest toServiceRequest(Context context) {
        return ServiceRecordResultRequest.builder()
                .result(result)
                .context(context)
                .build();
    }

}
