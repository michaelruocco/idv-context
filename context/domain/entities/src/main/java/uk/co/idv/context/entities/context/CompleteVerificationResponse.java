package uk.co.idv.context.entities.context;

import lombok.Builder;
import lombok.Data;
import uk.co.idv.context.entities.verification.Verification;
import uk.co.idv.method.entities.result.Result;

//TODO test
@Builder
@Data
public class CompleteVerificationResponse {

    private final Context original;
    private final Context updated;
    private final Verification verification;

    public Result getResult() {
        return verification.toResult();
    }

    public boolean isSequenceCompletedByResult() {
        return updated.hasMoreCompletedSequencesThan(original);
    }

    public boolean isMethodCompletedByResult() {
        return updated.hasMoreCompletedMethodsThan(original);
    }

}
