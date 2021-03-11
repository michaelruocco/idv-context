package uk.co.idv.context.usecases.context.verification;

import lombok.Builder;
import uk.co.idv.method.entities.verification.CompleteVerificationRequest;
import uk.co.idv.method.entities.verification.CompleteVerificationResult;
import uk.co.idv.method.entities.verification.CreateVerificationRequest;
import uk.co.idv.method.entities.verification.GetVerificationRequest;
import uk.co.idv.method.entities.verification.Verification;

@Builder
public class VerificationService {

    private final CreateVerification createVerification;
    private final CompleteVerification completeVerification;
    private final GetVerification getVerification;

    public Verification create(CreateVerificationRequest request) {
        return createVerification.create(request);
    }

    public CompleteVerificationResult complete(CompleteVerificationRequest request) {
        return completeVerification.complete(request);
    }

    public Verification get(GetVerificationRequest request) {
        return getVerification.get(request);
    }

}
