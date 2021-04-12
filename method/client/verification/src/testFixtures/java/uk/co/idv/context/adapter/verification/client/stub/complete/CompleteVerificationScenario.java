package uk.co.idv.context.adapter.verification.client.stub.complete;

import uk.co.idv.context.adapter.verification.client.request.ClientCompleteVerificationRequest;
import uk.co.idv.method.entities.verification.CompleteVerificationResult;

import java.util.function.Function;

public interface CompleteVerificationScenario extends Function<ClientCompleteVerificationRequest, CompleteVerificationResult> {

    boolean shouldExecute(ClientCompleteVerificationRequest request);

}
