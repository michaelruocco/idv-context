package uk.co.idv.context.entities.verification;

import lombok.Builder;
import lombok.Data;
import uk.co.idv.context.entities.context.Context;
import uk.co.idv.method.entities.result.Result;

@Builder
@Data
public class CompleteVerificationResponse {

    private final Context original;
    private final Context updated;
    private final Verification verification;

    public Result getResult() {
        return verification.toResult();
    }

    public boolean isSequenceCompletedByVerification() {
        return updated.hasMoreCompletedSequencesThan(original);
    }

    public boolean isMethodCompletedByVerification() {
        return updated.hasMoreCompletedMethodsThan(original);
    }

}
