package uk.co.idv.context.adapter.client;

import uk.co.idv.context.adapter.client.request.ClientCompleteVerificationRequest;
import uk.co.idv.context.adapter.client.request.ClientCreateVerificationRequest;
import uk.co.idv.method.entities.verification.Verification;

public interface VerificationClient {

    Verification createVerification(ClientCreateVerificationRequest request);

    Verification completeVerification(ClientCompleteVerificationRequest request);

}
