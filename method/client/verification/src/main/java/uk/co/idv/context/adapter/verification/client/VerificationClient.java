package uk.co.idv.context.adapter.verification.client;

import uk.co.idv.context.adapter.verification.client.request.ClientCompleteVerificationRequest;
import uk.co.idv.context.adapter.verification.client.request.ClientCreateVerificationRequest;
import uk.co.idv.method.entities.verification.CompleteVerificationResult;
import uk.co.idv.method.entities.verification.Verification;

public interface VerificationClient {

    Verification createVerification(ClientCreateVerificationRequest request);

    CompleteVerificationResult completeVerification(ClientCompleteVerificationRequest request);

}
