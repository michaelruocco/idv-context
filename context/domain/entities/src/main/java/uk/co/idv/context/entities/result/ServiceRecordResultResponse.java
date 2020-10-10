package uk.co.idv.context.entities.result;

import lombok.Builder;
import lombok.Data;
import uk.co.idv.context.entities.context.Context;
import uk.co.idv.method.entities.result.Result;

@Builder
@Data
public class ServiceRecordResultResponse {

    private final Context original;
    private final Context updated;
    private final Result result;

    public boolean isSequenceCompletedByResult() {
        return updated.hasMoreCompletedSequencesThan(original);
    }

    public boolean isMethodCompletedByResult() {
        return updated.hasMoreCompletedMethodsThan(original);
    }

}
