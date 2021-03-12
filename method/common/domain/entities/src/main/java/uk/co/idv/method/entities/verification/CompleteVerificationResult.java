package uk.co.idv.method.entities.verification;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CompleteVerificationResult {

    private final boolean contextComplete;
    private final boolean contextSuccessful;
    private final Verification verification;

    public boolean isVerificationComplete() {
        return verification.isComplete();
    }

    public boolean isVerificationSuccessful() {
        return verification.isSuccessful();
    }

}
