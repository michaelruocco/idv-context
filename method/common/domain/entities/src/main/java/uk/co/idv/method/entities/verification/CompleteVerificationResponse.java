package uk.co.idv.method.entities.verification;

import lombok.Builder;
import lombok.Data;
import uk.co.idv.method.entities.result.Result;

@Builder
@Data
public class CompleteVerificationResponse {

    private final boolean sequenceCompletedByVerification;
    private final boolean methodCompletedByVerification;
    private final Verification verification;

    public Result getResult() {
        return verification.toResult();
    }

    public boolean isSequenceCompletedByVerification() {
        return sequenceCompletedByVerification;
    }

    public boolean isMethodCompletedByVerification() {
        return methodCompletedByVerification;
    }

}
